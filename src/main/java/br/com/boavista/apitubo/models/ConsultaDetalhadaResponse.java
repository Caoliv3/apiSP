package br.com.boavista.apitubo.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDetalhadaResponse {
    private boolean success = true;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private ErrorResponse error;
    @Getter(AccessLevel.NONE)
    private List<Titulo> titulos;

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

    public List<Titulo> getTitulos(){
        if (this.titulos == null){
            this.titulos = new ArrayList<>();
        }
        return this.titulos;
    }

}
