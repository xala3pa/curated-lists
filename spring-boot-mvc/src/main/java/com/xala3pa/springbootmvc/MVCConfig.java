package com.xala3pa.springbootmvc;

import com.xala3pa.books.Interactor.FindAllBooksInteractor;
import com.xala3pa.books.Interactor.FindBookByIsbnInteractor;
import com.xala3pa.books.Interactor.SaveBookInteractor;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.Interactor.FindBookListsByAuthorInteractor;
import com.xala3pa.books.boundary.SaveBook;
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
  FindBookListByAuthor bookListByAuthor(BookGateway bookGateway) {
    return new FindBookListsByAuthorInteractor(bookGateway);
  }

  @Bean
  FindBookByIsbn bookByIsbn(BookGateway bookGateway) {
    return new FindBookByIsbnInteractor(bookGateway);
  }

  @Bean
  FindAllBooks allBooks(BookGateway bookGateway) {
    return new FindAllBooksInteractor(bookGateway);
  }

  @Bean
  SaveBook saveBook(BookGateway bookGateway) {
    return new SaveBookInteractor(bookGateway);
  }
}
