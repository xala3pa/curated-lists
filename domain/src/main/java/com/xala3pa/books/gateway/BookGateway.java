package com.xala3pa.books.gateway;

import com.xala3pa.books.Book;
import java.util.List;
import java.util.Optional;

public interface BookGateway {

  Optional<List<Book>> findAll();
  Optional<List<Book>> findByAuthor(String author);
  Optional<Book> getBookByIsbn(Long isbn);
}
