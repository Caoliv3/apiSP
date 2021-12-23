package br.com.boavista.apitubo.adapters.inbound;



import br.com.boavista.apitubo.infrastructure.ParametrosEntrada;
import br.com.boavista.apitubo.ports.inbound.RedisRequestPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisRequestPort redisService;

    @PostMapping(value = "/cache/incluir", produces = "application/json")
    public String incluirRegistoRedisCache(@RequestBody ParametrosEntrada parametroEntrada) {
        redisService.inicio();
        redisService.incluirRegistroCache(parametroEntrada.getDocumento());
        return "Documento incluido com sucesso";
    }

    @PostMapping(value = "/cache/consultar", produces = "application/json")
    public void consultaRegistoRedisCache(@RequestBody ParametrosEntrada parametroEntrada) {
        log.info("consultaRegistoRedisCache");
        redisService.inicio();
        redisService.consultaRegistroCache(parametroEntrada.getDocumento());
    }

    @PostMapping(value = "/cache/deletar", produces = "application/json")
    public void deletarRegistoCache(@RequestBody ParametrosEntrada parametroEntrada) {
        redisService.inicio();
        redisService.deletarRegistroCache(parametroEntrada.getDocumento());
    }

    @PostMapping(value = "/listanegra/incluir", produces = "application/json")
    public void incluirRegistoRedisListaNegra(@RequestBody ParametrosEntrada parametroEntrada) {
        redisService.inicio();
        redisService.incluirRegistroListaNegra(parametroEntrada.getDocumento());
    }

    @PostMapping(value = "/listanegra/consultar", produces = "application/json")
    public void consultaRegistoRedisListaNegra(@RequestBody ParametrosEntrada parametroEntrada) {
        redisService.inicio();
        redisService.consultaRegistroListaNegra(parametroEntrada.getDocumento());
    }

    @PostMapping(value = "/listanegra/deletar", produces = "application/json")
    public void deletarRegistoListaNegra(@RequestBody ParametrosEntrada parametroEntrada) {
        redisService.inicio();
        redisService.deletarRegistrolistaNegra(parametroEntrada.getDocumento());
    }
}
