package br.com.boavista.apitubo.core.usecases;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;
import br.com.boavista.apitubo.models.Protesto;
import br.com.boavista.apitubo.models.ResumoProtestos;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UseCaseSimplificada {

    private ConsultaPort consultas;
    private ProtestoPort baseProtestos;
    private DetalheProtestos detalheProtestos;
    private Auditoria auditoria;
    private Timestamp timestamp;

    public UseCaseSimplificada(ConsultaPort consultas, ProtestoPort baseProtestos, DetalheProtestos detalheProtestos) {
        this.consultas = consultas;
        this.baseProtestos = baseProtestos;
        this.detalheProtestos = detalheProtestos;
    }

    //Retorna consulta simplificada
    public String retornoConsultaSimplificada(ParametrosEntrada parametrosEntrada) {
        log.info("Consulta Simplificada - Inicio {} ",  LocalDateTime.now());
        String json;
        auditoria = new Auditoria();
        ConsultaSimplificadaResponse simplificadaResponse = consultas.fazerConsultaSimplificada(parametrosEntrada.getDocumento(), parametrosEntrada.getTipoPessoa());
        auditoria.setCodigoCliente(Integer.valueOf(parametrosEntrada.getCodigo()));
        auditoria.setCanal(parametrosEntrada.getCanalConsulta());
        auditoria.setProduto(parametrosEntrada.getFonte());
        auditoria.setDocumento(parametrosEntrada.getDocumento());
        auditoria.setTipoDocumento(Integer.valueOf(parametrosEntrada.getTipoPessoa()));
        auditoria.setFonteConsulta(2);
//        auditoria.setIdAuditoria(baseProtestos.getId());
        auditoria.setInicioConsulta(parametrosEntrada.getInicioConsulta());
        auditoria.setIdSimplificada(detalheProtestos.getId());

        if (simplificadaResponse.getCodigoRetono().equals("200")) {
            json = validaSimplificada(simplificadaResponse, parametrosEntrada.getDocumento(), parametrosEntrada.getTipoPessoa());
        } else {
            simplificadaResponse.setCodigoRetono("202");
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(simplificadaResponse));
            detalheProtestos.baixarProtesto(parametrosEntrada.getDocumento(),parametrosEntrada.getTipoPessoa());
            timestamp = new Timestamp(System.currentTimeMillis());
            auditoria.setFimConsulta(timestamp);
            this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            json = auditoria.getJsonSimplificada();
        }
        log.info("Consulta Simplificada - Fim {} ",  LocalDateTime.now());
        return json;
    }

    public String validaSimplificada(ConsultaSimplificadaResponse response, String documento, String tipoDocumento) {
        int qtdProtestosSimplificada = 0;
        int qtdProtestosBvs = 0;
        List<Protesto> protestoList = response.getProtestos();
        List<Protesto> lista = new ArrayList<>();
        detalheProtestos.setSimplificada(protestoList);
        for (Protesto totalProtestos : protestoList) {
            qtdProtestosSimplificada += Integer.valueOf(totalProtestos.getQuantidadeProtestos());
        }
        qtdProtestosBvs = baseProtestos.getQtdadeProtestoDocumento(documento, tipoDocumento);
        if (qtdProtestosBvs == qtdProtestosSimplificada) {
            response.setCodigoRetono("200");
        } else {
            detalheProtestos.setRecuperadaBase(this.baseProtestos.recuperarDetalheProtestos(documento));
            detalheProtestos.atualizarProstetos(documento, tipoDocumento);
            List<ResumoProtestos> resumoProtestosList  = detalheProtestos.getDetalhada();
            for (ResumoProtestos resumoProtestos : resumoProtestosList) {
                Protesto protesto = new Protesto();
                protesto.setTipoDocumento(resumoProtestos.getTipoDocumento());
                protesto.setDocumento(resumoProtestos.getDocumento());
                protesto.setIdCartorioBoavista(resumoProtestos.getIdCartorioBoavista());
                protesto.setQuantidadeProtestos(resumoProtestos.getQuantidadeProtestos());
                protesto.setValorProtestado(resumoProtestos.getValorProtestos());
                lista.add(protesto);
            }
            response.setProtestos(lista);
            response.setCodigoRetono("203");
        }
        auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
        auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(response));
        timestamp = new Timestamp(System.currentTimeMillis());
        auditoria.setFimConsulta(timestamp);
        this.baseProtestos.with(auditoria).salvar();
        return auditoria.getJsonSimplificada();
    }
}
