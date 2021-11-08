package br.com.boavista.apitubo.services;

import br.com.boavista.apitubo.adapters.outbound.ConsultaSoapAdapter;
import br.com.boavista.apitubo.adapters.outbound.ConsultaSoapFactory;
import br.com.boavista.apitubo.adapters.outbound.ProtestoRepository;
import br.com.boavista.apitubo.core.domain.*;
import br.com.boavista.apitubo.core.usecases.UseCaseCompleta;
import br.com.boavista.apitubo.core.usecases.UseCaseDetalhada;
import br.com.boavista.apitubo.core.usecases.UseCaseSimplificada;
import br.com.boavista.apitubo.infrastructure.Configuracao;
import br.com.boavista.apitubo.infrastructure.DataSource;
import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.ports.ConsultaFactory;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProtestoService implements ProtestoRequestPort {
    @Value("${service.apiprotestos.quantidade}")
    private String quantidades;
    @Value("${service.apiprotestos.dias-em-cache}")
    private String diasEmCache;
    @Value("${service.apiprotestos.atualizacao-forcada}")
    private String atualizacaoForcada;
    @Value("${service.apiprotestos.limite-diario}")
    private String limiteDiario;

    private ProtestoAtualizacao cache;
    private ProtestoLimiteDiario excedidoLimiteDiario;
    private ProtestoPort repository;
    private ConsultaPort consultaPort;
    private ConsultaFactory consultaFactory;
    private DetalheProtestos detalheProtestos;
    @Autowired
    private DataSource dataSource;

    private UseCaseCompleta useCaseCompleta;
    private UseCaseSimplificada useCaseSimplificada;
    private UseCaseDetalhada useCaseDetalhada;

    @Override
    public void iniciaProtestoService(){

        this.repository = new ProtestoRepository(dataSource.getConnection());
        this.consultaFactory = new ConsultaSoapFactory();
        consultaPort = new ConsultaSoapAdapter();
        consultaPort.setConsultaFactory(this.consultaFactory);
        this.detalheProtestos = new DetalheProtestos();

        this.useCaseCompleta = new UseCaseCompleta(this.cache, this.consultaPort, this.repository, this.excedidoLimiteDiario, this.detalheProtestos);
        this.useCaseSimplificada = new UseCaseSimplificada(this.consultaPort, this.repository, this.detalheProtestos);
    }

    @Override
    public String consultarSimplificada(ParametrosEntrada parametrosEntrada) {
        return this.useCaseSimplificada.retornoConsultaSimplificada(parametrosEntrada.getDocumento(), parametrosEntrada.getTipoPessoa());
    }

    @Override
    public String consultarDetalhada(String documento, String tipoDocumento, String idCartorio) {
        return this.useCaseDetalhada.retornoConsultaDetalhada(documento,tipoDocumento,idCartorio);
    }

    @Override
    public String consultar(ParametrosEntrada parametrosEntrada){
        String retorno = this.useCaseCompleta.retornarProtestos(parametrosEntrada.getDocumento(), parametrosEntrada.getTipoPessoa());
        return retorno;
    }

    private Configuracao getConfig() {
        Configuracao config = Configuracao.builder()
                .quantidadeProtestos(this.quantidades)
                .diasEmCache(this.diasEmCache)
                .atualizacaoForcada(this.atualizacaoForcada)
                .limiteDiario(this.limiteDiario)
                .build();
        return config;
    }
}