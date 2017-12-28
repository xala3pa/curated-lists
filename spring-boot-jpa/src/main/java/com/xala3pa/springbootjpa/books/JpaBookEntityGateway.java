package com.xala3pa.springbootjpa.books;


import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.springbootjpa.books.entity.BookEntity;
import com.xala3pa.springbootjpa.books.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JpaBookEntityGateway implements BookGateway {

  private final BookRepository bookRepository;

  public JpaBookEntityGateway(
      BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> findAll() {
    List<Book> books = new ArrayList<>();
    bookRepository.findAll().forEach(bookEntity -> books.add(mapToBook(bookEntity)));
    return books;
  }

  @Override
  public List<Book> findByAuthor(String author) {
    return bookRepository.findByAuthor(author).stream()
        .map(this::mapToBook)
        .collect(Collectors.toList());
  }

  @Override
  public Book getBookByIsbn(String isbn) {
    return mapToBook(bookRepository.getBookByIsbn(isbn));
  }

  private Book mapToBook(BookEntity bookEntity) {
    return Book.builder()
        .isbn(bookEntity.getIsbn())
        .title(bookEntity.getTitle())
        .author(bookEntity.getAuthor())
        .description(bookEntity.getDescription())
        .bookCategory(BookCategory.valueOf(bookEntity.getBookCategory().name()))
        .consumed(bookEntity.getConsumed())
        .dateAdded(bookEntity.getDateAdded()).build();
  }
}
