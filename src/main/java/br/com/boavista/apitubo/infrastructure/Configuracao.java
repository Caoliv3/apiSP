package br.com.boavista.apitubo.infrastructure;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Configuracao {
    private String quantidadeProtestos;
    private String diasEmCache;
    private String atualizacaoForcada;
    private String limiteDiario;
    private String canalConsulta;
    private String fonte;
    private String codigoCliente;
    private String tipoDocumento;
    private String documento;

}


