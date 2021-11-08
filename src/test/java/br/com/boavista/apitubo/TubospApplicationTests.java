//package br.com.boavista.apitubo;
//
//import br.com.boavista.apitubo.adapters.outbound.ConsultaTestAdapter;
//import br.com.boavista.apitubo.adapters.outbound.ConsultaTestFactory;
//import br.com.boavista.apitubo.adapters.outbound.ProtestoRepository;
//import br.com.boavista.apitubo.core.domain.DetalheProtestos;
//import br.com.boavista.apitubo.core.domain.ProtestoAtualizacao;
//import br.com.boavista.apitubo.core.domain.ValidadorProtestoAtualizacao;
//import br.com.boavista.apitubo.infrastructure.Configuracao;
//import br.com.boavista.apitubo.infrastructure.DataSourceConfigurationDevelopTest;
//import br.com.boavista.apitubo.infrastructure.DataSourceConfigurationTest;
//import br.com.boavista.apitubo.models.*;
//import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
//import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.Assert;
//
//@SpringBootTest
//class TubospApplicationTests {
//
//
//	@Value("${service.apitubo.protestos.quantidade}")
//	private String quantidades;
//	@Value("${service.apitubo.protestos.dias-em-cache}")
//	private String diasEmCache;
//	@Value("${service.apitubo.protestos.atualizacao-forcada}")
//	private String atualizacaoForcada;
//	@Value("${service.apitubo.protestos.limite-diario}")
//	private String limiteDiario;
//
//	private static ProtestoPort repository;
//	private static ConsultaTestFactory consultaTestFactory;
//	private static ConsultaPort consultas;
//	private ProtestoAtualizacao cache;
//
//	@Test
//	void contextLoads() {
//	}
//
//	@BeforeAll
//	public static void  init(){
//		DataSourceConfigurationTest dataSource = new DataSourceConfigurationDevelopTest();
//		consultaTestFactory = new ConsultaTestFactory();
//		consultas = new ConsultaTestAdapter();
//		consultas.setConsultaFactory(consultaTestFactory);
//		repository = new ProtestoRepository(dataSource.getConnection());
//	}
//
//	@Test
//	public void QtdeConsultaSimplificada_Igual_QtdeTitulosBase_entaoRetornaJsonBase(){
//
//		ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada("43131896884", "1");
//
//		DetalheProtestos detalheProtestos = new DetalheProtestos();
//		detalheProtestos.setRecuperadaBase(repository.recuperarDetalheProtestos("43131896884"));
//		detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());
//
//		detalheProtestos.atualizarProstetos("43131896884", "1");
//
//		Assert.isTrue(detalheProtestos.getIncluir().size() == 0, "inclusão indevida");
//		Assert.isTrue(detalheProtestos.getBaixar().size() == 0, "baixa indevida");
//		Assert.isTrue(detalheProtestos.getDetalhada().size() == 0, "consulta indevida");
//	}
//
//	@Test
//	public void QtdeConsultaSimplificada_Igual_QtdeTitulosBase_ValorProtestoDiferente(){
//		ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada("28787126000177", "2");
//
//		DetalheProtestos detalheProtestos = new DetalheProtestos();
//		detalheProtestos.setRecuperadaBase(repository.recuperarDetalheProtestos("28787126000177"));
//		detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());
//
//		detalheProtestos.atualizarProstetos("28787126000177", "2");
//		ConsultaDetalhadaResponse consultaDetalhada = consultas.fazerConsultaDetalhada(detalheProtestos.getDetalhada());
//		detalheProtestos.setIncluir(consultaDetalhada.getTitulos());
//
//		Assert.isTrue(detalheProtestos.getIncluir().size() > 0, "inclusão indevida");
//		Assert.isTrue(detalheProtestos.getBaixar().size() > 0, "baixa indevida");
//		Assert.isTrue(detalheProtestos.getDetalhada().size() > 0, "consulta indevida");
//	}
//
//	@Test
//	public void QtdeConsultaSimplificada_Diferente_QtdeTitulosBase_ValorProtestoDiferente() {
//
//		ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada("45543915000181", "2");
//
//		DetalheProtestos detalheProtestos = new DetalheProtestos();
//		detalheProtestos.setRecuperadaBase(repository.recuperarDetalheProtestos("45543915000181"));
//		detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());
//
//		detalheProtestos.atualizarProstetos("45543915000181", "2");
//		ConsultaDetalhadaResponse consultaDetalhada = consultas.fazerConsultaDetalhada(detalheProtestos.getDetalhada());
//		detalheProtestos.setIncluir(consultaDetalhada.getTitulos());
//
//		Assert.isTrue(detalheProtestos.getIncluir().size() > 0, "inclusão indevida");
//		Assert.isTrue(detalheProtestos.getBaixar().size() > 0, "baixa indevida");
//		Assert.isTrue(detalheProtestos.getDetalhada().size() > 0, "consulta indevida");
//	}
//
//	@Test
//	public void SemAtualizaçaoForcada_TitulosDentroValidade(){
//
//		Configuracao config = Configuracao.builder()
//				.quantidadeProtestos(this.quantidades)
//				.diasEmCache(this.diasEmCache)
//				.atualizacaoForcada(this.atualizacaoForcada)
//				.limiteDiario(this.limiteDiario)
//				.build();
//		this.cache = new ValidadorProtestoAtualizacao(config);
//		this.cache.setDataBase(repository.getDataAtualizacao("17611014000144"));
//		this.cache.setQuantidadeProtesto(repository.getQtdadeProtestoDocumento("17611014000144"));
//
//		Assert.isTrue(this.cache.atualizarProtestos() == false, "Consulta indevida");
//	}
//
//	@Test
//	public void SemAtualizaçaoForcada_TitulosForaValidade(){
//
//		Configuracao config = Configuracao.builder()
//				.quantidadeProtestos(this.quantidades)
//				.diasEmCache(this.diasEmCache)
//				.atualizacaoForcada(this.atualizacaoForcada)
//				.limiteDiario(this.limiteDiario)
//				.build();
//		this.cache = new ValidadorProtestoAtualizacao(config);
//		this.cache.setDataBase(repository.getDataAtualizacao("06967941879"));
//		this.cache.setQuantidadeProtesto(repository.getQtdadeProtestoDocumento("06967941879"));
//
//		Assert.isTrue(this.cache.atualizarProtestos() == true, "atualizacao de base");
//	}
//}
