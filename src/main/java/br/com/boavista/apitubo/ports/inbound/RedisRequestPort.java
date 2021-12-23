package br.com.boavista.apitubo.ports.inbound;


import br.com.boavista.apitubo.models.DocumentoCache;
import br.com.boavista.apitubo.models.ListaNegra;

public interface RedisRequestPort {
    void inicio();

    void incluirRegistroCache(String documento);

    void consultaRegistroCache(String documento);

    void deletarRegistroCache(String documento);

    void incluirRegistroListaNegra(String documento);

    void consultaRegistroListaNegra(String documento);

    void deletarRegistrolistaNegra(String documento);
}

