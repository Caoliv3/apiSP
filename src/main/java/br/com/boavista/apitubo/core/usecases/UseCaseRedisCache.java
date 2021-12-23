package br.com.boavista.apitubo.core.usecases;


import br.com.boavista.apitubo.adapters.outbound.repository.DocumentoCacheRepository;
import br.com.boavista.apitubo.infrastructure.ParametroEntradaException;
import br.com.boavista.apitubo.models.DocumentoCache;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UseCaseRedisCache {

    private DocumentoCache documentoCache;
    private DocumentoCacheRepository repository;

    public UseCaseRedisCache(DocumentoCacheRepository repository, DocumentoCache documentoCache) {
        this.repository = repository;
        this.documentoCache = documentoCache;
    }

    public void incluirRegistro(String documento) {
        documentoCache.setDocumento(documento);
        documentoCache.setExpira(48);
        repository.save(documentoCache);
    }

    public void consultaRegistro(String documento) {
        log.info("Usecase - consultaRegistoRedisCache");
        if (repository.findById(documento).isPresent()) {
            throw new ParametroEntradaException("Documento encontra-se em cache, o mesmo expira em " + documentoCache.getExpira() + " Hora(s)");
        }
        throw new ParametroEntradaException("Documento não encontra-se em cache");
    }

    public void deletarRegistro(String documento) {
        if (repository.findById(documento).isPresent()) {
            repository.deleteById(documento);
            throw new ParametroEntradaException("Documento excluido do Cache");
        }
        throw new ParametroEntradaException("Documento nao existe no Cache");
    }
}

