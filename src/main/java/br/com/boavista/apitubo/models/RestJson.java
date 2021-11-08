package br.com.boavista.apitubo.models;


import lombok.Data;

import java.util.List;

@Data
public class RestJson {

    private int code = 0;
    private String codeMessage = "";
    private String documento;
    private String tipoDocumento;
    private String qtdeCartorios;
    private String qtdeProtestos;
    private List<RestSP> data;
}
