package br.com.boavista.apitubo.models;



import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestSP {
    private String codigo;
    private String atualizacaoData;
    private int quantidade;
    private List<RestProtesto> protestos = new ArrayList<>();

}
