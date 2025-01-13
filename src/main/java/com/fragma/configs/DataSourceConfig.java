/*
package com.fragma.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class DataSourceConfig {


    */
/*@Bean(name = "mysqlDataSourceProperties")
    @ConfigurationProperties(prefix = "local.mysql.db")
    public DataSourceProperties mysqlDataSourceProperties() {
        return new DataSourceProperties();
    }*//*


    @Bean
    public DataSource mysqlDataSource(@Qualifier("mysqlDataSourceProperties") DataSourceProperties mysqlDataSourceProperties) {
        String driverClassName = mysqlDataSourceProperties.getDriverClassName();
        String password = mysqlDataSourceProperties.getPassword();
        String username = mysqlDataSourceProperties.getUsername();
        String url = mysqlDataSourceProperties.getUrl();
        List<String> data = mysqlDataSourceProperties.getData();
        System.out.println(data);

        return mysqlDataSourceProperties.initializeDataSourceBuilder().build();
    }
}
*/
