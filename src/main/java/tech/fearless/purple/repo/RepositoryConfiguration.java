package tech.fearless.purple.repo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"tech.fearless.purple.entity"})
@EnableJpaRepositories(basePackages = {"tech.fearless.purple.repo"})
@EnableTransactionManagement
public class RepositoryConfiguration { }
