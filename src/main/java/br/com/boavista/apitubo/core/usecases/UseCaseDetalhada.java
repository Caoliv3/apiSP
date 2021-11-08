package br.com.boavista.apitubo.core.usecases;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.ConsultaDetalhadaResponse;
import br.com.boavista.apitubo.models.ResumoProtestos;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import java.util.ArrayList;
import java.util.List;

public class UseCaseDetalhada {

    private ConsultaPort consultas;
    private ProtestoPort baseProtestos;
    private DetalheProtestos detalheProtestos;
    private Auditoria auditoria = new Auditoria();

    public UseCaseDetalhada(ConsultaPort consultas, ProtestoPort baseProtestos, DetalheProtestos detalheProtestos) {
        this.consultas = consultas;
        this.baseProtestos = baseProtestos;
        this.detalheProtestos = detalheProtestos;
    }

    public String retornoConsultaDetalhada(String documento, String tipoDocumento, String idCartorio) {
        String json;
        ResumoProtestos resumoProtestos = new ResumoProtestos();
        List<ResumoProtestos> resumoProtestosList = new ArrayList<>();
        resumoProtestos.setIdCartorioBoavista(idCartorio);
        resumoProtestos.setTipoDocumento(tipoDocumento);
        resumoProtestos.setDocumento(documento);
        resumoProtestosList.add(resumoProtestos);

        detalheProtestos.setDetalhada(resumoProtestosList);
        auditoria.setFonteConsulta(3);
        auditoria.setIdDetalhada(detalheProtestos.getId());
        auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
        auditoria.setIdAuditoria(detalheProtestos.getId());
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
        return json;
    }
}
