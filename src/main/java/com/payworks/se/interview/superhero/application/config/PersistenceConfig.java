package com.payworks.se.interview.superhero.application.config;

import com.payworks.se.interview.superhero.domain.Superhero;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = Superhero.class)
public class PersistenceConfig {
}
