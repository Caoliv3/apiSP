package br.com.boavista.apitubo.models;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Protesto implements Serializable  {
    private static final long serialVersionUID = -3269834206223758002L;
    private String tipoDocumento;
    private String documento;
    private String codigoCidade;
    private String codigoCartorio;
    private String idCartorioBoavista;
    private String quantidadeProtestos;
    private String valorProtestado;
    private String anuencia;
    private String nomeDevedor;
    private String telefoneDevedor;
    private String enderecoDevedor;
    private String cidadeDevedor;
    private String ufed;
}
