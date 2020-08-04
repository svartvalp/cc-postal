package com.crashcourse;

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
        liquibase.setDataSource(dataSource.getIfAvailable());
        liquibase.setDefaultSchema("postal_schema");
        liquibase.setLiquibaseSchema("postal_schema");
        liquibase.setChangeLog("classpath:db/db-master-changelog.xml");
        return liquibase;
    }
}