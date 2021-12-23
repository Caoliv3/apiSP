package br.com.boavista.apitubo.adapters.inbound;

import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Slf4j
@RestController
public class apiProtestoController {

	@Autowired
	private ProtestoRequestPort protestoService;

	private Timestamp timestamp;

	@GetMapping(value = "/v1/consulta-completa/{tipodocumento}/{documento}/{canalconsulta}/{produto}/{codigocliente}")
	public String InitTuboSP(@PathVariable("tipodocumento") String tipoDocumento, @PathVariable String documento,
							 @PathVariable("canalconsulta") String canalConsulta, @PathVariable String produto, @PathVariable("codigocliente") String codigoCliente) {
		beforeClass();
		timestamp = new Timestamp(System.currentTimeMillis());
		ParametrosEntrada parametrosEntrada = ParametrosEntrada.builder().tipoDocumento(tipoDocumento).canalConsulta(canalConsulta).fonte(produto)
				.documento(documento).codigo(codigoCliente).inicioConsulta(timestamp).build();
		parametrosEntrada.validarDados();
		protestoService.iniciaProtestoService();
		return protestoService.consultarCompleta(parametrosEntrada);

	}

	@PostMapping(value = "/v1/consulta-completa", produces = "application/json")
	public String InitApiProtesto(@RequestBody ParametrosEntrada parametrosEntrada) {
		beforeClass();
		String responseJson = null;
		parametrosEntrada.validarDados();
		protestoService.iniciaProtestoService();
		timestamp = new Timestamp(System.currentTimeMillis());
		parametrosEntrada.setInicioConsulta(timestamp);
		return protestoService.consultarCompleta(parametrosEntrada);
	}

	public static void beforeClass() {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger");
		System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
	}

	@GetMapping(value = "/bvs-health")
	public ResponseEntity<String> getHealth() {
		return new ResponseEntity<String>("{'status' : 'UP'}", HttpStatus.OK);
	}

	@GetMapping(value = "/v1/consulta-simplificada/{tipodocumento}/{documento}/{canalconsulta}/{produto}/{codigocliente}")
	public String getSimplificada(@PathVariable String documento, @PathVariable("tipodocumento") String tipoDocumento,
								  @PathVariable("canalconsulta") String canalConsulta, @PathVariable String produto, @PathVariable("codigocliente") String codigoCliente) {

		timestamp = new Timestamp(System.currentTimeMillis());
		ParametrosEntrada parametrosEntrada = ParametrosEntrada.builder().tipoDocumento(tipoDocumento).canalConsulta(canalConsulta).fonte(produto)
				.documento(documento).codigo(codigoCliente).inicioConsulta(timestamp).build();
		parametrosEntrada.validarDados();
		protestoService.iniciaProtestoService();
		return protestoService.consultarSimplificada(parametrosEntrada);
	}

	@GetMapping(value = "/v1/consulta-detalhada/{tipodocumento}/{documento}/{idcartorio}")
	public String getDetalhada(@PathVariable String documento, @PathVariable("tipodocumento") String tipoDocumento, @PathVariable("idcartorio") String idCartorio) {

		timestamp = new Timestamp(System.currentTimeMillis());
		ParametrosEntrada parametrosEntrada = ParametrosEntrada.builder().tipoDocumento(tipoDocumento).documento(documento).codigo("00000000").build();
		parametrosEntrada.validarDados();
		protestoService.iniciaProtestoService();
		return protestoService.consultarDetalhada(documento, tipoDocumento, idCartorio);
	}
}
