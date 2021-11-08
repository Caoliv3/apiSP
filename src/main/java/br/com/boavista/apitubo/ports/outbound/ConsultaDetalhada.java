package br.com.boavista.apitubo.ports.outbound;

import java.util.List;

import br.com.boavista.apitubo.models.ConsultaDetalhadaFiltro;
import br.com.boavista.apitubo.models.Titulo;

public interface ConsultaDetalhada {
	List<Titulo> consultar(ConsultaDetalhadaFiltro filtro);
}
