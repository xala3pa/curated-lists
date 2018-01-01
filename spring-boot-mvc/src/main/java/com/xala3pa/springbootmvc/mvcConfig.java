package com.xala3pa.springbootmvc;

import com.xala3pa.books.boundary.BookListByAuthor;
import com.xala3pa.books.Interactor.GetBookListsByAuthor;
import com.xala3pa.books.gateway.BookGateway;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class mvcConfig {

  @Bean
  BookListByAuthor bookListByAuthor(BookGateway bookGateway) {
    return new GetBookListsByAuthor(bookGateway);
  }
}
