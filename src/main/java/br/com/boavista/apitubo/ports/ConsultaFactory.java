package br.com.boavista.apitubo.ports;

import br.com.boavista.apitubo.ports.outbound.ConsultaDetalhada;
import br.com.boavista.apitubo.ports.outbound.ConsultaSimplificada;

public interface ConsultaFactory {
    ConsultaSimplificada getConsultaSimplificada();
    ConsultaDetalhada getConsultaDetalhada();
}
