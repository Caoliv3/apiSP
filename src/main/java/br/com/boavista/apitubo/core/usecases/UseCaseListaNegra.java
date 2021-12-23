package br.com.boavista.apitubo.core.usecases;


import br.com.boavista.apitubo.adapters.outbound.repository.ListaNegraRepository;
import br.com.boavista.apitubo.infrastructure.ParametroEntradaException;
import br.com.boavista.apitubo.models.ListaNegra;

public class UseCaseListaNegra {

    private ListaNegra listaNegra;
    private ListaNegraRepository repository;

    public UseCaseListaNegra(ListaNegraRepository repository, ListaNegra documentoCache) {
        this.repository = repository;
        this.listaNegra = documentoCache;
    }

    public void incluirRegistro(String documento) {
        if (documento.length() > 11) {
            listaNegra.setDocumento(documento.substring(0, 8));
        } else {
            listaNegra.setDocumento(documento);
        }
        repository.save(listaNegra);
    }

    public ListaNegra consultaRegistro(String documento) {
        if (repository.findById(documento).isPresent()) {
            throw new ParametroEntradaException("Documento cadastrado na lista negra " + documento);
        }
        throw new ParametroEntradaException("Documento nao encontra-se na lista negra");
    }

    public void deletarRegistro(String documento) {
        if (repository.findById(documento).isPresent()) {
            repository.deleteById(documento);
            throw new ParametroEntradaException("Documento excluido da lista negra");
        }
        throw new ParametroEntradaException("Documento nao existe na lista negra");
    }
}

