package com.pruebatec.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
public class postgreConfig {

//    @Autowired
//    private Environment environment;
//
//    @Bean(name = "userDataSource")
//    public DataSource userDatasource(){
//        DriverManagerDataSource dataSource=new DriverManagerDataSource();
//        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//        dataSource.setUsername(environment.getProperty("spring..datasource.username"));
//        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
//
//        return dataSource;
//    }


}
