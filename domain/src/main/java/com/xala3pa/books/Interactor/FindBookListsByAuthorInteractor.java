package com.xala3pa.books.Interactor;

import com.xala3pa.books.Book;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindBookListsByAuthorInteractor implements FindBookListByAuthor {

  private BookGateway bookGateway;

  public FindBookListsByAuthorInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public List<BookOutputData> getBooks(BookListByAuthorInputData bookListByAuthorInputData) {
    Optional<List<Book>> books = bookGateway.findByAuthor(bookListByAuthorInputData.getAuthor());

    if (!books.isPresent()) {
      throw new BooksNotFoundException();
    }

    return books.get().stream().map(Book::mapToBookOutputData).collect(Collectors.toList());
  }
}
