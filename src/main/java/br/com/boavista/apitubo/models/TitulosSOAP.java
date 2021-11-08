package br.com.boavista.apitubo.models;

public class TitulosSOAP {
	
	public String tipo_documento_devedor;
	public String documento_devedor;
	public String nome_devedor;
	public String endereco_devedor;
	public String cep_devedor;
	public String bairro_devedor;
	public String cidade_devedor;
	public String uf_devedor;
	public String livro_protesto;
	public String folha_protesto;
	public String protocolo;
	public String data_protocolo;
	public String especie;
	public String numero_titulo;
	public String data_emissao;
	public String data_vencimento;
	public String data_protesto;
	public String valor_original;
	public String valor_protestado;

	public TitulosSOAP(){}

	public TitulosSOAP(String tipo_documento_devedor, String documento_devedor, String nome_devedor, String endereco_devedor, String cep_devedor, String bairro_devedor, String cidade_devedor, String uf_devedor, String livro_protesto, String folha_protesto, String protocolo, String data_protocolo, String especie, String numero_titulo, String data_emissao, String data_vencimento, String data_protesto, String valor_original, String valor_protestado) {
		this.tipo_documento_devedor = tipo_documento_devedor;
		this.documento_devedor = documento_devedor;
		this.nome_devedor = nome_devedor;
		this.endereco_devedor = endereco_devedor;
		this.cep_devedor = cep_devedor;
		this.bairro_devedor = bairro_devedor;
		this.cidade_devedor = cidade_devedor;
		this.uf_devedor = uf_devedor;
		this.livro_protesto = livro_protesto;
		this.folha_protesto = folha_protesto;
		this.protocolo = protocolo;
		this.data_protocolo = data_protocolo;
		this.especie = especie;
		this.numero_titulo = numero_titulo;
		this.data_emissao = data_emissao;
		this.data_vencimento = data_vencimento;
		this.data_protesto = data_protesto;
		this.valor_original = valor_original;
		this.valor_protestado = valor_protestado;
	}

	@Override
	public String toString() {
		return "TitulosSOAP{" +
				"tipo_documento_devedor='" + tipo_documento_devedor + '\'' +
				", documento_devedor='" + documento_devedor + '\'' +
				", nome_devedor='" + nome_devedor + '\'' +
				", endereco_devedor='" + endereco_devedor + '\'' +
				", cep_devedor='" + cep_devedor + '\'' +
				", bairro_devedor='" + bairro_devedor + '\'' +
				", cidade_devedor='" + cidade_devedor + '\'' +
				", uf_devedor='" + uf_devedor + '\'' +
				", livro_protesto='" + livro_protesto + '\'' +
				", folha_protesto='" + folha_protesto + '\'' +
				", protocolo='" + protocolo + '\'' +
				", data_protocolo='" + data_protocolo + '\'' +
				", especie='" + especie + '\'' +
				", numero_titulo='" + numero_titulo + '\'' +
				", data_emissao='" + data_emissao + '\'' +
				", data_vencimento='" + data_vencimento + '\'' +
				", data_protesto='" + data_protesto + '\'' +
				", valor_original='" + valor_original + '\'' +
				", valor_protestado='" + valor_protestado + '\'' +
				'}';
	}
}
