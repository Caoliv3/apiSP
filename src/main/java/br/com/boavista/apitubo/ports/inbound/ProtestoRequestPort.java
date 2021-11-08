package br.com.boavista.apitubo.ports.inbound;

import br.com.boavista.apitubo.infrastructure.Configuracao;
import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;

public interface ProtestoRequestPort {
    String consultar(ParametrosEntrada parametrosEntrada);
    void iniciaProtestoService();
    String consultarSimplificada(ParametrosEntrada parametrosEntrada);

    String consultarDetalhada(String documento, String tipoDocumento, String idCartorio);
}
