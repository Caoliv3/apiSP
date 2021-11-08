package br.com.boavista.apitubo.adapters.inbound;

import br.com.boavista.apitubo.infrastructure.Configuracao;
import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.models.EntityParameters;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TuboSPController {

	@Autowired
	private ProtestoRequestPort protestoService;

    @GetMapping(value = "/v1/consulta-completa/{tipodocumento}/{documento}")
	public String InitTuboSP(@PathVariable("tipodocumento") String tipoDocumento , @PathVariable String documento) {
		beforeClass();
		String responseJson = null;
		protestoService.iniciaProtestoService();
		ParametrosEntrada parametrosEntrada = ParametrosEntrada.builder().tipoDocumento(tipoDocumento).documento(documento).codigoCliente("00000000").build();
        parametrosEntrada.validarDados();
		responseJson = protestoService.consultar(parametrosEntrada);
		return responseJson;
	}

	@PostMapping(value = "/v1/consulta-completa")
	public String InitApiProtesto(@RequestBody ParametrosEntrada parametrosEntrada) {
		beforeClass();
		String responseJson = null;
		parametrosEntrada.validarDados();
		protestoService.iniciaProtestoService();
		responseJson = protestoService.consultar(parametrosEntrada);
		return responseJson;
	}
	
	public static void beforeClass() {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger");
		System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
	}

	@GetMapping(value = "/bvs-health")
	public ResponseEntity<String> getHealth(){
		return new ResponseEntity<String>("{'status' : 'UP'}", HttpStatus.OK);
	}

	@GetMapping(value = "/v1/consulta-simplificada/{tipodocumento}/{documento}")
	public String getSimplificada(@PathVariable String documento, @PathVariable("tipodocumento") String tipoDocumento){
		protestoService.iniciaProtestoService();
        ParametrosEntrada parametrosEntrada = ParametrosEntrada.builder().tipoDocumento(tipoDocumento).documento(documento).codigoCliente("00000000").build();
        parametrosEntrada.validarDados();
		return protestoService.consultarSimplificada(parametrosEntrada);
	}

	@GetMapping(value = "/v1/consulta-simplificada/{tipodocumento}/{documento}/{idcartorio}")
	public String getDetalhada(@PathVariable String documento, @PathVariable("tipodocumento") String tipoDocumento, @PathVariable("idcartorio") String idCartorio){
		protestoService.iniciaProtestoService();
        ParametrosEntrada parametrosEntrada = ParametrosEntrada.builder().tipoDocumento(tipoDocumento).documento(documento).codigoCliente("00000000").build();
        parametrosEntrada.validarDados();
		return protestoService.consultarDetalhada(documento,tipoDocumento, idCartorio);
	}
}
