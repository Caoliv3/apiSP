package br.com.boavista.apitubo.models;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChaveComposta implements Serializable{
	private String id_cartorio;
	private String documento;
	private String datahora ;
	private String identificador;
}
