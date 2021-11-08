package br.com.boavista.apitubo.ports.outbound;

import br.com.boavista.apitubo.models.ConsultaDetalhadaResponse;
import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;
import br.com.boavista.apitubo.models.ResumoProtestos;
import br.com.boavista.apitubo.ports.ConsultaFactory;

import java.util.List;

public interface ConsultaPort {
    void setConsultaFactory(ConsultaFactory consultaFactory);
    ConsultaSimplificadaResponse fazerConsultaSimplificada(String documento, String tipoDocumento);
    ConsultaDetalhadaResponse fazerConsultaDetalhada(List<ResumoProtestos> protestos);
}
