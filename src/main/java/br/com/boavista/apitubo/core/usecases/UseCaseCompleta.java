package br.com.boavista.apitubo.core.usecases;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
public class UseCaseCompleta {

    private ConsultaPort consultas;
    private ProtestoPort baseProtestos;
    private DetalheProtestos detalheProtestos;
    private Auditoria auditoria;
    private Timestamp timestamp;


    public UseCaseCompleta(ConsultaPort consultas, ProtestoPort baseProtestos, DetalheProtestos detalheProtestos) {
        this.consultas = consultas;
        this.baseProtestos = baseProtestos;
        this.detalheProtestos = detalheProtestos;
    }

    public String retornarProtestos(ParametrosEntrada parametrosEntrada) {
        log.info("Consulta Completa - Documento {} - Inicio {} ", parametrosEntrada.getDocumento(), LocalDateTime.now());
        String retorno = null;
        auditoria = new Auditoria();
        auditoria.setTipoDocumento(Integer.valueOf(parametrosEntrada.getTipoPessoa()));
        auditoria.setCodigoCliente(Integer.valueOf(parametrosEntrada.getCodigo()));
        auditoria.setCanal(parametrosEntrada.getCanalConsulta());
        auditoria.setProduto(parametrosEntrada.getFonte());
        auditoria.setDocumento(parametrosEntrada.getDocumento());
        auditoria.setInicioConsulta(parametrosEntrada.getInicioConsulta());
//        auditoria.setIdAuditoria(this.baseProtestos.getId());
        detalheProtestos.setRecuperadaBase(this.baseProtestos.recuperarDetalheProtestos(parametrosEntrada.getDocumento()));

        log.info("Consulta Completa Documento {} - Fim {}", parametrosEntrada.getDocumento(), LocalDateTime.now());
        return getConsulta(parametrosEntrada.getDocumento(), parametrosEntrada.getTipoPessoa());

    }

    public String getConsulta(String documento, String tipoDocumento) {
        String json = null;
        ConsultaSimplificadaResponse simplificadaResponse = consultas.fazerConsultaSimplificada(documento, tipoDocumento);

        auditoria.setFonteConsulta(2);
        auditoria.setIdSimplificada(this.baseProtestos.getId());
        auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(simplificadaResponse));
        detalheProtestos.setSimplificada(simplificadaResponse.getProtestos());
        if (simplificadaResponse.isSuccess()) {
            detalheProtestos.atualizarProstetos(documento, tipoDocumento);
            if (detalheProtestos.getDetalhada().size() > 0) {
                json = getConsultaDetalhada(documento, tipoDocumento);
            } else {
                timestamp = new Timestamp(System.currentTimeMillis());
                json = detalheProtestos.getJson(documento, tipoDocumento);
                auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
                auditoria.setFimConsulta(timestamp);
                this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            }
        } else {
            json = detalheProtestos.getErrorJson(simplificadaResponse.getError());
            auditoria.setJsonAuditoria(json);
            timestamp = new Timestamp(System.currentTimeMillis());
            auditoria.setFimConsulta(timestamp);
            if (simplificadaResponse.getError().getCodigo() == 612) {
                detalheProtestos.baixarProtesto(documento, tipoDocumento);
                this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            } else {
                this.baseProtestos.with(auditoria).salvar();
            }
        }
        return json;
    }

    public String getConsultaDetalhada(String documento, String tipoDocumento) {
//        log.info("[API-SPY] Atualizacao Base BVS Detalhada - Inicio {}", LocalDateTime.now());
        String json;
        auditoria.setFonteConsulta(3);
        auditoria.setIdDetalhada(this.baseProtestos.getId());
        auditoria.setQuantidadeConsulta(detalheProtestos.getDetalhada().size());
        ConsultaDetalhadaResponse detalhadaResponse = consultas.fazerConsultaDetalhada(detalheProtestos.getDetalhada());
        if (detalhadaResponse.isSuccess()) {
            detalheProtestos.setIncluir(detalhadaResponse.getTitulos());
            json = detalheProtestos.getJson(documento, tipoDocumento);
            auditoria.setJsonDetalhada(json);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            timestamp = new Timestamp(System.currentTimeMillis());
            auditoria.setFimConsulta(timestamp);
            this.baseProtestos.with(auditoria).salvar(detalheProtestos);
        } else {
            json = detalheProtestos.getErrorJson(detalhadaResponse.getError());
            auditoria.setJsonDetalhada(json);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            timestamp = new Timestamp(System.currentTimeMillis());
            auditoria.setFimConsulta(timestamp);
            this.baseProtestos.with(auditoria).salvar();
        }
//        log.info("[API-SPY] Atualizacao Base BVS Detalhada - Fim {}", LocalDateTime.now());
        return json;
    }
}
