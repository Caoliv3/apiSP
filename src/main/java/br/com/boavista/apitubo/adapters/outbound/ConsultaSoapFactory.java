package br.com.boavista.apitubo.adapters.outbound;

import br.com.boavista.apitubo.ports.ConsultaFactory;
import br.com.boavista.apitubo.ports.outbound.ConsultaDetalhada;
import br.com.boavista.apitubo.ports.outbound.ConsultaSimplificada;
import org.springframework.stereotype.Component;

@Component
public class ConsultaSoapFactory implements ConsultaFactory {
    public ConsultaSimplificada getConsultaSimplificada(){
        return new ConsultaSimplificadaSoapAdapter();
    }

    public ConsultaDetalhada getConsultaDetalhada(){
        return new ConsultaDetalhadaSoapAdapter();
    }
}
