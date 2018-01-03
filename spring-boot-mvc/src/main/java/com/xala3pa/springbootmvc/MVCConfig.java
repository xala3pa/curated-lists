package com.xala3pa.springbootmvc;

import com.xala3pa.books.Interactor.GetBookByIsbn;
import com.xala3pa.books.boundary.BookByIsbn;
import com.xala3pa.books.boundary.BookListByAuthor;
import com.xala3pa.books.Interactor.GetBookListsByAuthor;
import com.xala3pa.books.gateway.BookGateway;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.xala3pa"})
public class MVCConfig {

  @Bean
  BookListByAuthor bookListByAuthor(BookGateway bookGateway) {
    return new GetBookListsByAuthor(bookGateway);
  }

  @Bean
  BookByIsbn bookByIsbn(BookGateway bookGateway) {
    return new GetBookByIsbn(bookGateway);
  }
}
