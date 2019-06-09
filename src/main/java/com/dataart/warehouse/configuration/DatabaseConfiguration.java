package com.dataart.warehouse.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.dataart.warehouse.repository")
@EntityScan("com.dataart.warehouse.model")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
