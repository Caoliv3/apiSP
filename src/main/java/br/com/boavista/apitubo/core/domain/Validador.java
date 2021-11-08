package br.com.boavista.apitubo.core.domain;

public class Validador {
    public static boolean ehDocumentoValido(String cpfcnpj, String tipodoc){
        Integer tipoDocumento = Integer.parseInt(tipodoc);
        Integer comprimentoDocumento = cpfcnpj.length();
        boolean retorno = true;

        if(tipoDocumento.equals(1)) {
            if(!comprimentoDocumento.equals(11))
                retorno = false;
        } else if(tipoDocumento.equals(2)) {
            if(!comprimentoDocumento.equals(14))
                retorno = false;
        }
        return retorno;
    }
}
