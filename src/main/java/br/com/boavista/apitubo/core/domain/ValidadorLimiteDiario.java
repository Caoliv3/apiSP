package br.com.boavista.apitubo.core.domain;

import br.com.boavista.apitubo.infrastructure.Configuracao;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;

public class ValidadorLimiteDiario implements ProtestoLimiteDiario {

    private int limiteDiario;
    private ProtestoPort repository =  null;

    public ValidadorLimiteDiario(ProtestoPort repository, Configuracao conf) {
        this.repository = repository;
        this.limiteDiario = Integer.parseInt(conf.getLimiteDiario());
    }

   @Override
    public boolean excedeuLimiteDiario(){

         boolean retorno = false;
         int qtdConsultas = repository.getQtdadeConsultas();
         if(qtdConsultas >= this.limiteDiario){
             retorno = true;
         }
        return retorno;
    }
}
