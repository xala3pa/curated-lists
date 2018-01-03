package com.xala3pa.springbootjpa.books;


import com.xala3pa.books.Book;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.springbootjpa.books.entity.BookEntity;
import com.xala3pa.springbootjpa.books.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class JpaBookEntityGateway implements BookGateway {

  private final BookRepository bookRepository;

  public JpaBookEntityGateway(
      BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAll() {
    List<Book> books = new ArrayList<>();
    bookRepository.findAll().forEach(bookEntity -> books.add(bookEntity.mapToBook()));
    return books;
  }

  @Override
  public Optional<List<Book>> findByAuthor(String author) {
    return bookRepository.findByAuthor(author)
        .map(bookEntities -> bookEntities.stream().map(BookEntity::mapToBook)
            .collect(Collectors.toList()));
  }

  @Override
  public Optional<Book> getBookByIsbn(Long isbn) {
    return bookRepository.getBookByIsbn(isbn).map(BookEntity::mapToBook);
  }

}
