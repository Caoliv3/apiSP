package br.com.boavista.apitubo.services;


import br.com.boavista.apitubo.adapters.outbound.repository.DocumentoCacheRepository;
import br.com.boavista.apitubo.adapters.outbound.repository.ListaNegraRepository;
import br.com.boavista.apitubo.core.usecases.UseCaseListaNegra;
import br.com.boavista.apitubo.core.usecases.UseCaseRedisCache;
import br.com.boavista.apitubo.models.DocumentoCache;
import br.com.boavista.apitubo.models.ListaNegra;
import br.com.boavista.apitubo.ports.inbound.RedisRequestPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisService implements RedisRequestPort {

    @Autowired
    private DocumentoCacheRepository repositoryCache;
    @Autowired
    private ListaNegraRepository repositoryListaNegra;
    private DocumentoCache documentoCache;
    private ListaNegra listaNegra;
    private UseCaseRedisCache useCaseRedisCache;
    private UseCaseListaNegra useCaseListaNegra;

    @Override
    public void inicio() {
        documentoCache = new DocumentoCache();
        listaNegra = new ListaNegra();
        useCaseRedisCache = new UseCaseRedisCache(repositoryCache, documentoCache);
        useCaseListaNegra = new UseCaseListaNegra(repositoryListaNegra, listaNegra);
    }

    @Override
    public void incluirRegistroCache(String documento) {
        useCaseRedisCache.incluirRegistro(documento);
    }

    @Override
    public void consultaRegistroCache(String documento) {
        log.info("Service - consultaRegistoRedisCache");
        useCaseRedisCache.consultaRegistro(documento);
    }

    @Override
    public void deletarRegistroCache(String documento) {
        useCaseRedisCache.deletarRegistro(documento);
    }

    @Override
    public void incluirRegistroListaNegra(String documento) {
        useCaseListaNegra.incluirRegistro(documento);
    }

    @Override
    public void consultaRegistroListaNegra(String documento) {
        useCaseListaNegra.consultaRegistro(documento);
    }

    @Override
    public void deletarRegistrolistaNegra(String documento) {
        useCaseListaNegra.deletarRegistro(documento);
    }
}
