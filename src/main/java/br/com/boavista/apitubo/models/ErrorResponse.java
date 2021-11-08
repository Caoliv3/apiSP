package br.com.boavista.apitubo.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int codigo;
    private String descricao;
    private String detalhes;
    private String documento;
    private String tipoDocumento;
}
