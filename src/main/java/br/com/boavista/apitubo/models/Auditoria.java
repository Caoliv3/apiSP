package br.com.boavista.apitubo.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Auditoria {

    public int quantidadeConsulta;
    private int idAuditoria;
    private String idSimplificada;
    private String idDetalhada;
    private String jsonSimplificada;
    private String jsonDetalhada;
    private String jsonAuditoria;
    private int tipoDocumento;
    private String documento;
    private int fonteConsulta;
    private String canal;
    private String produto;
    private int codigoCliente;
    private Timestamp inicioConsulta;
    private Timestamp fimConsulta;
}
