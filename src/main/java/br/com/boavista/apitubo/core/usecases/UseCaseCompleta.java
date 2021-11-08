package br.com.boavista.apitubo.core.usecases;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.core.domain.ProtestoAtualizacao;
import br.com.boavista.apitubo.core.domain.ProtestoLimiteDiario;
import br.com.boavista.apitubo.core.domain.Validador;
import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UseCaseCompleta {
    private ProtestoAtualizacao cache;
    private ConsultaPort consultas;
    private ProtestoPort baseProtestos;
    private DetalheProtestos detalheProtestos;
    private ProtestoLimiteDiario limiteDiario;
    private Auditoria auditoria = new Auditoria();


    public UseCaseCompleta(ProtestoAtualizacao cache, ConsultaPort consultas, ProtestoPort baseProtestos, ProtestoLimiteDiario limiteDiario, DetalheProtestos detalheProtestos) {
        this.cache = cache;
        this.consultas = consultas;
        this.baseProtestos = baseProtestos;
        this.limiteDiario = limiteDiario;
        this.detalheProtestos = detalheProtestos;
    }

    public String retornarProtestos(String documento, String tipoDocumento) {
        log.info("Consulta Completa - Inicio");
        String retorno = null;
        if (Validador.ehDocumentoValido(documento, tipoDocumento)) {
            auditoria.setTipoDocumento(Integer.valueOf(tipoDocumento));
            auditoria.setDocumento(documento);
            auditoria.setIdAuditoria(detalheProtestos.getId());
            detalheProtestos.setRecuperadaBase(this.baseProtestos.recuperarDetalheProtestos(documento));
            retorno = getConsulta(documento, tipoDocumento);
        } else {
            ErrorResponse error = ErrorResponse.builder()
                    .codigo(613)
                    .descricao("DOCUMENTO INVALIDO")
                    .documento(documento)
                    .tipoDocumento(tipoDocumento)
                    .build();
            retorno = detalheProtestos.getErrorJson(error);
        }
        return retorno;
    }

    public String getConsulta(String documento, String tipoDocumento) {
        log.info("Consulta Completa - simplificada");
        String json = null;
        ConsultaSimplificadaResponse simplificadaResponse = consultas.fazerConsultaSimplificada(documento, tipoDocumento);

        auditoria.setFonteConsulta(2);
        auditoria.setIdSimplificada(detalheProtestos.getId());
        auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(simplificadaResponse));
        detalheProtestos.setSimplificada(simplificadaResponse.getProtestos());
        if (simplificadaResponse.isSuccess()) {
            detalheProtestos.atualizarProstetos(documento, tipoDocumento);
            if (detalheProtestos.getDetalhada().size() > 0) {
                json = getConsultaDetalhada(documento, tipoDocumento);
            } else {
                json = detalheProtestos.getJson(documento, tipoDocumento);
                auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
                this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            }
        } else {
            json = detalheProtestos.getErrorJson(simplificadaResponse.getError());
            auditoria.setJsonAuditoria(json);
            if (simplificadaResponse.getError().getCodigo() == 612) {
                detalheProtestos.baixarProtesto(documento, tipoDocumento);
                this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            }else {
                this.baseProtestos.with(auditoria).salvar();
            }
        }
        return json;
    }

    public String getConsultaDetalhada(String documento, String tipoDocumento) {
        log.info("Consulta Completa - detalhada");
        String json;
        auditoria.setFonteConsulta(3);
        auditoria.setIdDetalhada(detalheProtestos.getId());
        auditoria.setQuantidadeConsulta(detalheProtestos.getDetalhada().size());
        ConsultaDetalhadaResponse detalhadaResponse = consultas.fazerConsultaDetalhada(detalheProtestos.getDetalhada());
        if (detalhadaResponse.isSuccess()) {
            detalheProtestos.setIncluir(detalhadaResponse.getTitulos());
            json = detalheProtestos.getJson(documento, tipoDocumento);
            auditoria.setJsonDetalhada(json);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            this.baseProtestos.with(auditoria).salvar(detalheProtestos);
        } else {
            json = detalheProtestos.getErrorJson(detalhadaResponse.getError());
            auditoria.setJsonDetalhada(json);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
        }
        log.info("Consulta Completa - Fim");
        return json;
    }
}
