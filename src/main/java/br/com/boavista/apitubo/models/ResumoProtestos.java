package br.com.boavista.apitubo.models;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumoProtestos implements Serializable {
	private static final long serialVersionUID = 6647787560432926704L;
	public String tipoDocumento;
	public String documento;
	public String idCartorioBoavista;
	public String quantidadeProtestos;
	public String valorProtestos;

}

