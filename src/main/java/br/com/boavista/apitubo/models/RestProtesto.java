package br.com.boavista.apitubo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestProtesto {
    private String cpfCnpj;
    private String dataProtesto;
    private String dataVencimento;
    private String valor;
    private String uf_cartorio;
    private String especie;
}

