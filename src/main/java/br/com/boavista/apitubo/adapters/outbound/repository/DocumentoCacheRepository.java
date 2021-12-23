package br.com.boavista.apitubo.adapters.outbound.repository;

import br.com.boavista.apitubo.models.DocumentoCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoCacheRepository extends CrudRepository<DocumentoCache, String> {
}

