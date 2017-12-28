package com.xala3pa.books.gateway;

import com.xala3pa.books.Book;
import java.util.List;

public interface BookGateway {

  List<Book> findAll();
  List<Book> findByAuthor(String author);
  Book getBookByIsbn(String isbn);
}
