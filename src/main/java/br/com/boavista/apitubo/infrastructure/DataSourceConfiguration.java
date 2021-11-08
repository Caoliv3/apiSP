package br.com.boavista.apitubo.infrastructure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
public class DataSourceConfiguration implements DataSource {
    private HikariConfig config;
    private HikariDataSource ds;

    @Value("${service.apiprotestos.servidor}")
    private String servidor;
    @Value("${service.apiprotestos.usuario}")
    private String usuario;
    @Value("${service.apiprotestos.senha}")
    private String senha;


    @Override
    public Connection getConnection() {

        if (this.config == null){
            this.config = new HikariConfig();
            config.setJdbcUrl( servidor );
            config.setUsername( usuario );
            config.setPassword( senha );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "25" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            ds = new HikariDataSource( config );
        }
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            log.info("SQLERROr - Nao foi possivel conectar a base de dados {}" , e.getMessage());
            return null;
        }
    }
}
