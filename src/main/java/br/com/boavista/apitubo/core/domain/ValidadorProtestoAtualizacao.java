package br.com.boavista.apitubo.core.domain;

import br.com.boavista.apitubo.infrastructure.Configuracao;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ValidadorProtestoAtualizacao implements ProtestoAtualizacao {
    private String quantidades;
    private String diasEmCache;
    private String atualizacaoForcada;
    private Integer quantidadeProtesto;
    private String dataBase;

    public ValidadorProtestoAtualizacao(Configuracao config){
        this.quantidades = config.getQuantidadeProtestos();
        this.diasEmCache = config.getDiasEmCache();
        this.atualizacaoForcada = config.getAtualizacaoForcada();
    }

	@Override
    public boolean atualizarProtestos() {
        boolean retorno = true;
        if(this.dataBase == null) {
            return true;
        }
        long diasCorridos = this.calcularDiasCorridos(this.dataBase);

        String[] qtdes = quantidades.split(",");
        String[] diasCache = diasEmCache.split(",");
        String[] atualizacao = atualizacaoForcada.split(",");

        for (int i = 0; i <= qtdes.length; i++) {
            if (this.quantidadeProtesto <= Integer.valueOf(qtdes[i])) {
                if (atualizacao[i].equals("0")) {
                    if (diasCorridos > Integer.valueOf(diasCache[i])) {
                        retorno = true;
                        break;
                    } else {
                        retorno = false;
                        break;
                    }
                } else {
                    retorno = true;
                    break;
                }
            }
        }
        return retorno;
    }

    private long calcularDiasCorridos(String dataBase2) {
    	LocalDate d1 = LocalDate.parse(dataBase2, DateTimeFormatter.ISO_LOCAL_DATE);
    	LocalDate d2 = LocalDate.now();
    	Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
    	long diffDays = diff.toDays();
    	return diffDays;
    }

    @Override
    public void setQuantidadeProtesto(Integer quantidadeProtesto) {
        this.quantidadeProtesto = quantidadeProtesto;
    }

    @Override
    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
}
