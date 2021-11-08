package br.com.boavista.apitubo.models;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaSimplificadaResponse {
    private boolean success = true;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private ErrorResponse error;
    private String retorno;
    private String codigoRetono;
    private String dataConsulta;
    private String situacao;
    private String valorTotalProtestos;
    @Getter(AccessLevel.NONE)
    private List<Protesto> protestos;


    public ErrorResponse getError(){
        if (this.error == null){
            this.error = new ErrorResponse();
            this.success = false;
        }
        return this.error;
    }

    public void setError(ErrorResponse error){
        this.error = error;
        this.success = false;
    }

    public List<Protesto> getProtestos(){
        if (this.protestos == null){
            this.protestos = new ArrayList<>();
        }
        return this.protestos;
    }
}
