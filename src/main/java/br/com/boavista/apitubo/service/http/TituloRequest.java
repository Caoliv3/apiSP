package br.com.boavista.apitubo.service.http;

import java.util.List;
import java.util.concurrent.Callable;

import br.com.boavista.apitubo.models.ConsultaDetalhadaFiltro;
import br.com.boavista.apitubo.models.Titulo;
import br.com.boavista.apitubo.ports.outbound.ConsultaDetalhada;

public class TituloRequest implements Callable<List<Titulo>> {
	private final ConsultaDetalhada consultaDetalhada;
	private final ConsultaDetalhadaFiltro consultaDetalhadaFiltro;
	
	public TituloRequest(ConsultaDetalhada consultaDetalhada, ConsultaDetalhadaFiltro filtro) {
		this.consultaDetalhada = consultaDetalhada;
		this.consultaDetalhadaFiltro = filtro;
	}
	
	@Override
	public List<Titulo> call() {

		return this.consultaDetalhada.consultar(this.consultaDetalhadaFiltro);
	}
}