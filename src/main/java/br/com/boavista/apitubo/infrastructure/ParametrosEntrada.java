package br.com.boavista.apitubo.infrastructure;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParametrosEntrada {

    private String canalConsulta;
    private String fonte;
    private String codigoCliente;
    private String tipoDocumento;
    private String documento;
    private String tipoPessoa;


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

        if(codigoCliente.isEmpty()){
            throw  new ParametroEntradaException("Codigo de Cliente nao informado");
        }
    }
}
