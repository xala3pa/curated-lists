package com.xala3pa.books.Interactor;

import com.xala3pa.books.Book;
import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.Optional;

public class FindBookByIsbnInteractor implements FindBookByIsbn {

  private BookGateway bookGateway;

  public FindBookByIsbnInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public BookOutputData getBook(BookByIsbnInputData bookByIsbnInputData) {
    Optional<Book> book = bookGateway.getBookByIsbn(bookByIsbnInputData.getIsbn());

    if(!book.isPresent()) {
      throw new BooksNotFoundException();
    }

    return book.get().mapToBookOutputData();
  }
}
