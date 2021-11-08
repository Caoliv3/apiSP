package br.com.boavista.apitubo.core.usecases;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;
import br.com.boavista.apitubo.models.Protesto;
import br.com.boavista.apitubo.models.ResumoProtestos;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UseCaseSimplificada {

    private ConsultaPort consultas;
    private ProtestoPort baseProtestos;
    private DetalheProtestos detalheProtestos;
    private Auditoria auditoria = new Auditoria();

    public UseCaseSimplificada(ConsultaPort consultas, ProtestoPort baseProtestos, DetalheProtestos detalheProtestos) {
        this.consultas = consultas;
        this.baseProtestos = baseProtestos;
        this.detalheProtestos = detalheProtestos;
    }

    //Retorna consulta simplificada
    public String retornoConsultaSimplificada(String documento, String tipoDocumento) {
        log.info("Consulta Simplificada - Inicio");
        String json;
        ConsultaSimplificadaResponse simplificadaResponse = consultas.fazerConsultaSimplificada(documento, tipoDocumento);
        if (simplificadaResponse.getCodigoRetono().equals("200")) {
            json = validaSimplificada(simplificadaResponse, documento, tipoDocumento);
        } else {
            simplificadaResponse.setCodigoRetono("202");
            auditoria.setDocumento(documento);
            auditoria.setTipoDocumento(Integer.valueOf(tipoDocumento));
            auditoria.setFonteConsulta(2);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            auditoria.setIdAuditoria(detalheProtestos.getId());
            auditoria.setIdSimplificada(detalheProtestos.getId());
            auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(simplificadaResponse));
            detalheProtestos.baixarProtesto(documento,tipoDocumento);
            this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            json = auditoria.getJsonSimplificada();
        }
        log.info("Consulta Simplificada - Fim");
        return json;
    }

    public String validaSimplificada(ConsultaSimplificadaResponse response, String documento, String tipoDocumento) {
        int qtdProtestosSimplificada = 0;
        int qtdProtestosBvs = 0;
        List<Protesto> protestoList = response.getProtestos();
        List<Protesto> lista = new ArrayList<>();
        auditoria.setDocumento(documento);
        auditoria.setTipoDocumento(Integer.valueOf(tipoDocumento));
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
            for (ResumoProtestos resumoProtestos : detalheProtestos.getDetalhada()) {
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
        auditoria.setFonteConsulta(2);
        auditoria.setIdAuditoria(detalheProtestos.getId());
        auditoria.setIdSimplificada(detalheProtestos.getId());
        auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(response));
        this.baseProtestos.with(auditoria).salvar();
        return auditoria.getJsonSimplificada();
    }
}
