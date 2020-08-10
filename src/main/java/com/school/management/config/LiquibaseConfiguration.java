package com.school.management.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {

  @Bean
  public SpringLiquibase liquibase(ObjectProvider<DataSource> dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:db/liquibase/db-master.yaml");
    liquibase.setDataSource(dataSource.getIfAvailable());
    return liquibase;
  }
}