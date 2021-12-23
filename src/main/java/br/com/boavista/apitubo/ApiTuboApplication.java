package br.com.boavista.apitubo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class ApiTuboApplication {

	@Value("${service.apiprotestos.servidor}")
	private String servidor;
	@Value("${service.apiprotestos.usuario}")
	private String usuario;
	@Value("${service.apiprotestos.senha}")
	private String senha;

	public static void main(String[] args) {
		SpringApplication.run(ApiTuboApplication.class, args);
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource(){
		HikariConfig config = new HikariConfig();;
		HikariDataSource dataSource = null;

		log.info("[API-STA] Url do servidor: {}", servidor);
		config.setJdbcUrl(servidor);
		config.setUsername(usuario);
		config.setPassword(senha);
		config.setAutoCommit(false);
		config.setMinimumIdle(10);
		config.setMaximumPoolSize(30);
		config.addDataSourceProperty( "cachePrepStmts" , "true" );
		config.addDataSourceProperty( "prepStmtCacheSize" , "25" );
		config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
		dataSource = new HikariDataSource(config);

		return dataSource;
	}
}
