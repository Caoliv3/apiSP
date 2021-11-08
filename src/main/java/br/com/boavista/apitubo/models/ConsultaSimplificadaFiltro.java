package br.com.boavista.apitubo.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaSimplificadaFiltro {
	private String documento;
	private String tipoDocumento;
}

