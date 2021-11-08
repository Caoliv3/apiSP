package br.com.boavista.apitubo.models;

public class CartorioSOAP {
	public String codigo_cidade;
	public String codigo_cartorio;
	public String id_cartorio_boavista;
	public String nome;
	public String telefone;
	public String endereco;
	public String cidade;
	public String protestos;
	public String anuencia;
	public String valor_protestado;

	@Override
	public String toString() {
		return "CartorioSOAP{" +
				"codigo_cidade='" + codigo_cidade + '\'' +
				", codigo_cartorio='" + codigo_cartorio + '\'' +
				", id_cartorio_boavista='" + id_cartorio_boavista + '\'' +
				", nome='" + nome + '\'' +
				", telefone='" + telefone + '\'' +
				", endereco='" + endereco + '\'' +
				", cidade='" + cidade + '\'' +
				", protestos='" + protestos + '\'' +
				", anuencia='" + anuencia + '\'' +
				", valor_protestado='" + valor_protestado + '\'' +
				'}';
	}
}
