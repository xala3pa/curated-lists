package com.xala3pa.books.Interactor;

import com.xala3pa.books.Book;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindAllBooksInteractor implements FindAllBooks {

  private BookGateway bookGateway;

  public FindAllBooksInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public List<BookOutputData> getBooks() {
    Optional<List<Book>> books = bookGateway.findAll();

    if (!books.isPresent()) {
      throw new BooksNotFoundException();
    }

    return books.get().stream().map(Book::mapToBookOutputData).collect(Collectors.toList());
  }
}
