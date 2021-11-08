package br.com.boavista.apitubo.models;

import lombok.Data;
import java.util.List;

@Data
public class AuditoriaJson {

    private List<Titulo> baseProtesto;
    private List<ResumoProtestos> baixaProtesto;
    private List<Titulo> incluirProtesto;

}
