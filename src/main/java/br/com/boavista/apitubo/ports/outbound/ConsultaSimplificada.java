package br.com.boavista.apitubo.ports.outbound;

import br.com.boavista.apitubo.models.ConsultaSimplificadaFiltro;
import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;

public interface ConsultaSimplificada {
	ConsultaSimplificadaResponse consultar(ConsultaSimplificadaFiltro filtro);
}
