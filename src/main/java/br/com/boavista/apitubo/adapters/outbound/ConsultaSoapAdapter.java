package br.com.boavista.apitubo.adapters.outbound;

import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.ConsultaFactory;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.service.http.TituloRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class ConsultaSoapAdapter implements ConsultaPort {
    private ConsultaFactory consultaFactory;
    private String documento;
    private String tipoDocumento;

    @Override
    public void setConsultaFactory(ConsultaFactory consultaFactory) {
        this.consultaFactory = consultaFactory;
    }

    @Override
    public ConsultaSimplificadaResponse fazerConsultaSimplificada(String documento, String tipoDocumento) {
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        return this.consultaFactory.getConsultaSimplificada().consultar(new ConsultaSimplificadaFiltro(documento, tipoDocumento));
    }

    @Override
    public ConsultaDetalhadaResponse fazerConsultaDetalhada(List<ResumoProtestos> protestos) {
        log.info("consultar detalhada - inicio {}", LocalDateTime.now());
        ExecutorService executor = Executors.newFixedThreadPool(protestos.size() > 120 ? 120 : protestos.size());
        List<Callable<List<Titulo>>> callables = new ArrayList<>();

        for (ResumoProtestos protesto : protestos) {
            callables.add(new TituloRequest(this.consultaFactory.getConsultaDetalhada(), ConsultaDetalhadaFiltro.buildFromProtesto(protesto)));
        }
        ConsultaDetalhadaResponse response = new ConsultaDetalhadaResponse();
        List<Titulo> titulos = response.getTitulos();
        try {
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            log.info("Erro" + e.getMessage());
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(data -> titulos.addAll(data));
        } catch (InterruptedException e) {
            this.fillFrom(response, new ErrorResponse(500, "Erro ao executar consulta detalhada!", e.getMessage(), documento, tipoDocumento));
        }
        executor.shutdown();
        log.info("consultar detalhada - fim {}", LocalDateTime.now());
        return response;
    }

    public void fillFrom(ConsultaDetalhadaResponse response, ErrorResponse error) {
        response.setError(error);
    }
}
