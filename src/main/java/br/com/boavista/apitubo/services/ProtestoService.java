package br.com.boavista.apitubo.services;

import br.com.boavista.apitubo.adapters.outbound.ConsultaSoapAdapter;
import br.com.boavista.apitubo.adapters.outbound.ConsultaSoapFactory;
import br.com.boavista.apitubo.adapters.outbound.repository.DocumentoCacheRepository;
import br.com.boavista.apitubo.adapters.outbound.repository.ListaNegraRepository;
import br.com.boavista.apitubo.adapters.outbound.repository.ProtestoRepository;
import br.com.boavista.apitubo.core.domain.*;
import br.com.boavista.apitubo.core.usecases.*;
import br.com.boavista.apitubo.infrastructure.ParametroEntradaException;
import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.models.DocumentoCache;
import br.com.boavista.apitubo.ports.ConsultaFactory;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Slf4j
@Service
public class ProtestoService implements ProtestoRequestPort {

    private ProtestoPort repository;
    private ConsultaPort consultaPort;
    private ConsultaFactory consultaFactory;
    private DetalheProtestos detalheProtestos;

    private UseCaseCompleta useCaseCompleta;
    private UseCaseSimplificada useCaseSimplificada;
    private UseCaseDetalhada useCaseDetalhada;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DocumentoCacheRepository repositoryCache;
    @Autowired
    private ListaNegraRepository repositoryListaNegra;
    private DocumentoCache documentoCache;


    @Override
    public void iniciaProtestoService() {

        this.repository = new ProtestoRepository(dataSource);
        this.consultaFactory = new ConsultaSoapFactory();
        consultaPort = new ConsultaSoapAdapter();
        consultaPort.setConsultaFactory(this.consultaFactory);
        this.detalheProtestos = new DetalheProtestos();

        this.useCaseCompleta = new UseCaseCompleta(this.consultaPort, this.repository, this.detalheProtestos);
        this.useCaseSimplificada = new UseCaseSimplificada(this.consultaPort, this.repository, this.detalheProtestos);
    }

    @Override
    public String consultarSimplificada(ParametrosEntrada parametrosEntrada) {
        return this.useCaseSimplificada.retornoConsultaSimplificada(parametrosEntrada);
    }

    @Override
    public String consultarDetalhada(String documento, String tipoDocumento, String idCartorio) {
        return this.useCaseDetalhada.retornoConsultaDetalhada(documento, tipoDocumento, idCartorio);
    }


    @Override
    public String consultarCompleta(ParametrosEntrada parametrosEntrada) {
        verificaRestricao(parametrosEntrada.getDocumento(), parametrosEntrada.getTipoPessoa());
        return this.useCaseCompleta.retornarProtestos(parametrosEntrada);
    }

    private void verificaRestricao(String documento, String tipoPessoa) {
        if (repositoryListaNegra.findById(documento).isPresent()) {
            throw new ParametroEntradaException("Documento encontra-se na lista negra " + documento);
        }
        String cacheDocumento = tipoPessoa.equals("1") ? documento : documento.substring(0, 8);

        if (repositoryCache.findById(cacheDocumento).isPresent()) {
            throw new ParametroEntradaException("Documento consultado recentemente " + documento);
        } else {
            documentoCache = new DocumentoCache();
            if (documento.length() > 11) {
                documentoCache.setDocumento(cacheDocumento);
            } else {
                documentoCache.setDocumento(cacheDocumento);
            }
            documentoCache.setExpira(48);
            repositoryCache.save(documentoCache);
        }
    }
}