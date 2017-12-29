package com.xala3pa.springbootjpa.books;

import com.xala3pa.springbootjpa.books.repository.BookRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.xala3pa.springbootjpa")
@EntityScan(basePackages = "com.xala3pa.springbootjpa")
public class RepositoryConfig {

  @Bean
  public JpaBookEntityGateway jpaBookEntityGateway(BookRepository bookRepository) {
    return new JpaBookEntityGateway(bookRepository);
  }
}
