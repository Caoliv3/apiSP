package br.com.boavista.apitubo.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaDetalhadaFiltro {
	private String tipoDocumento;
	private String documento;
	private String numeroCartorio;
	
	public static ConsultaDetalhadaFiltro buildFromProtesto(ResumoProtestos protesto) {
		ConsultaDetalhadaFiltro filtro = new ConsultaDetalhadaFiltro();
		filtro.setTipoDocumento(protesto.getTipoDocumento());
		filtro.setDocumento(protesto.getDocumento());
		filtro.setNumeroCartorio(protesto.getIdCartorioBoavista());

		return filtro;
	}
}
