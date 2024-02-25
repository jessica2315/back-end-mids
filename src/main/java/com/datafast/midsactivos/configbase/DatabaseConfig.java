package com.datafast.midsactivos.configbase;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration 
public class DatabaseConfig {
	
	
    @Bean(name = "dsDfreportes")
    @ConfigurationProperties(prefix = "ds.dfreportes")
    public DataSource dataSourceDfreportes() { 
        return DataSourceBuilder.create().build();
    }
     
    @Bean(name = "jdbcDfreportes") 
    @Autowired
    public JdbcTemplate jdbcTemplateDfreportes(@Qualifier("dsDfreportes") DataSource dsDfreportes)  {
        return new JdbcTemplate(dsDfreportes); 
    }	
}
