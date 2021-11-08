//package br.com.boavista.apitubo.infrastructure;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Component
//@Slf4j
//public class DataSourceConfigurationDevelopTest implements DataSourceConfigurationTest {
//    private HikariConfig config;
//    private HikariDataSource ds;
//
//
//    @Override
//    public Connection getConnection() {
//        if (this.config == null){
//            this.config = new HikariConfig();
//            config.setJdbcUrl( "jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM './sql/schema.sql'\\;RUNSCRIPT FROM './sql/data.sql'");
//            config.setUsername( "h2sa" );
//            config.setPassword( "admin" );
//            config.addDataSourceProperty( "cachePrepStmts" , "true" );
//            config.addDataSourceProperty( "prepStmtCacheSize" , "25" );
//            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//            ds = new HikariDataSource( config );
//        }
//        try {
//            return ds.getConnection();
//        } catch (SQLException e) {
//            log.info("SQLERROr - Nao foi possivel conectar a base de dados {}" , e.getMessage());
//            return null;
//        }
//    }
//}
