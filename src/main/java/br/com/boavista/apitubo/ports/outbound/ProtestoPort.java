package br.com.boavista.apitubo.ports.outbound;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.Titulo;

import java.util.List;

public interface ProtestoPort {
    List<Titulo> recuperarDetalheProtestos(String documento);
    String recuperarJsonConsultaDetalhada(String documento);
    ProtestoPort with(Auditoria auditoria);
    ProtestoPort salvar();
    ProtestoPort salvar(DetalheProtestos detalheProtestos);
    int getQtdadeProtestoDocumento(String documento, String tipoDocumento);
    String getDataAtualizacao(String documento);
    int getQtdadeConsultas();

    String getId();
}
