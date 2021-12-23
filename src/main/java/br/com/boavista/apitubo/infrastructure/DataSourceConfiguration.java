//package br.com.boavista.apitubo.infrastructure;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//
//@Slf4j
//@Component
//public class DataSourceConfiguration implements DataSource {
//
//    @Value("${service.apiprotestos.servidor}")
//    private String servidor;
//    @Value("${service.apiprotestos.usuario}")
//    private String usuario;
//    @Value("${service.apiprotestos.senha}")
//    private String senha;
//
//    @Bean
//    @Override
//    public Connection getConnection(){
//        HikariConfig config = new HikariConfig();;
//        HikariDataSource dataSource = null;
//
//        log.info("[API-STA] Url do servidor: {}", servidor);
//        config.setJdbcUrl(servidor);
//        config.setUsername(usuario);
//        config.setPassword(senha);
//        config.setAutoCommit(false);
//        config.setMinimumIdle(10);
//        config.setMaximumPoolSize(30);
//        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//        config.addDataSourceProperty( "prepStmtCacheSize" , "25" );
//        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//        dataSource = new HikariDataSource(config);
//
//        try {
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            log.info("SQLERROr - Nao foi possivel conectar a base de dados {}" , e.getMessage());
//            return null;
//        }
//    }
//}
