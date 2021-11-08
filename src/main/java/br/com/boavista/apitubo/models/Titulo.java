package br.com.boavista.apitubo.models;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Titulo implements Serializable {
	private static final long serialVersionUID = 5882586700709970844L;
	private String cod_cartorio;
	private String tipo_documento_devedor;
	private String documento_devedor;
	private String nome_devedor;
	private String endereco_devedor;
	private String cep_devedor;
	private String bairro_devedor;
	private String cidade_devedor;
	private String uf_devedor;
	private String livro_protesto;
	private String folha_protesto;
	private String protocolo;
	private String data_protocolo;
	private String especie;
	private String numero_titulo;
	private String data_emissao;
	private String data_vencimento;
	private String data_protesto;
	private String valor_original;
	private String valor_protestado;
	private String erro_metodo;
	private String erro_metodo_descricao;
	private String data_inclusao;
	private String idProtesto;

}

