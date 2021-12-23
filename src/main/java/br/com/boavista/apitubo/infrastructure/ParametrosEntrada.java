package br.com.boavista.apitubo.infrastructure;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ParametrosEntrada {

    private String canalConsulta;
    private String fonte;
    private String codigo;
    private String tipoDocumento;
    private String documento;
    private String tipoPessoa;
    private Timestamp inicioConsulta;
    private String tipoAcao;


    public void validarDados() {
        CPFValidator cpfValidator = new CPFValidator();
        CNPJValidator cnpjValidator = new CNPJValidator();

        if (!(tipoDocumento.toLowerCase().equals("pf") || tipoDocumento.toLowerCase().equals("pj"))) {
            throw new ParametroEntradaException("Tipo de documento diferente de PF e PJ");
        }

        if (tipoDocumento.toLowerCase().equals("pf")) {
            try {
                cpfValidator.assertValid(documento);
                tipoPessoa = "1";
            } catch (Exception e) {
                throw new ParametroEntradaException("Documento PF Invalido : " + documento);
            }
        } else
            try {
                cnpjValidator.assertValid(documento);

                tipoPessoa = "2";
            } catch (Exception e) {
                throw new ParametroEntradaException("Documento PJ Invalido : " + documento);
            }

        if (codigo.isEmpty()) {
            throw new ParametroEntradaException("Codigo de Cliente nao informado");
        }

        if (canalConsulta.isEmpty()) {
            throw new ParametroEntradaException("Canal Consulta nao informado");
        }

        if (fonte.isEmpty()) {
            throw new ParametroEntradaException("Produto/Fonte nao informado");
        }

    }
}


