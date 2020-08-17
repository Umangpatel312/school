package com.school.management.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfiguration {

	Logger logger=LoggerFactory.getLogger(LiquibaseConfiguration.class);
	
	@Bean
	@Profile("dev")
	public SpringLiquibase liquibase(ObjectProvider<DataSource> dataSource) {
		logger.error("dev liquibase executed:");
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:db/liquibase/master.yaml");
		liquibase.setDataSource(dataSource.getIfAvailable());
		return liquibase;
	}

	@Profile("test")
	@Bean
	public SpringLiquibase liquibaseTest(ObjectProvider<DataSource> dataSource) {
		logger.error("test liquibase executed");
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:db/liquibase/master-test.yaml");
		liquibase.setDataSource(dataSource.getIfAvailable());
		return liquibase;
	}
}