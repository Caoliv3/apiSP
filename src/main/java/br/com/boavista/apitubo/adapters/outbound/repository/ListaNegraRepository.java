package br.com.boavista.apitubo.adapters.outbound.repository;


import br.com.boavista.apitubo.models.ListaNegra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaNegraRepository extends CrudRepository<ListaNegra, String> {
}

