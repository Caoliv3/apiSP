package br.com.boavista.apitubo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CabecalhoSimplificadaSOAP {

    public String data_consulta;
    public String retorno;
    public String erro;
    public String erro_descricao;
    public String situacao;
    public String valor_protestos;

    @Override
    public String toString() {
        return "CabecalhoSimplicadaSOAP{" +
                "data_consulta='" + data_consulta + '\'' +
                ", retorno='" + retorno + '\'' +
                ", erro='" + erro + '\'' +
                ", erro_descricao='" + erro_descricao + '\'' +
                ", situacao='" + situacao + '\'' +
                ", valor_protestos='" + valor_protestos + '\'' +
                '}';
    }
}
