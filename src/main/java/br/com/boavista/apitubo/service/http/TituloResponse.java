package br.com.boavista.apitubo.service.http;

import java.util.List;

import br.com.boavista.apitubo.models.Titulo;
import lombok.Data;

@Data
public class TituloResponse {
	private List<Titulo> dados; 
	public TituloResponse(List<Titulo> dados) {
		this.dados = dados;
	}
}