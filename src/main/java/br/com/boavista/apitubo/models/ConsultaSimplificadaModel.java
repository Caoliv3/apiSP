package br.com.boavista.apitubo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultaSimplificadaModel {
	public String documento;
	public String id_cartorio_boavista;
	public String protestos;
	public String data_consulta;
	public String retorno;
	public String erro;
	public String erro_descricao;
	public String situacao;
	public String valor_protestados_total;
	public List<CartorioSOAP> conteudo;
}
