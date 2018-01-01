package com.xala3pa.books.Interactor;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import com.xala3pa.books.boundary.BookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.List;
import java.util.stream.Collectors;

public class GetBookListsByAuthor implements BookListByAuthor {

  private BookGateway bookGateway;

  public GetBookListsByAuthor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public List<BookOutputData> getBooks(BookListByAuthorInputData bookListByAuthorInputData) {
    List<Book> books = bookGateway.findByAuthor(bookListByAuthorInputData.getAuthor());

    if (books.isEmpty()) {
      throw new BooksNotFoundException();
    }

    return books.stream().map(this::mapToBookOutputData).collect(Collectors.toList());
  }

  BookOutputData mapToBookOutputData(Book book) {
    return BookOutputData.builder()
        .isbn(book.getIsbn())
        .author(book.getAuthor())
        .description(book.getDescription())
        .bookCategory(BookCategory.valueOf(book.getBookCategory().name()))
        .bookStatus(BookStatus.valueOf(book.getBookStatus().name()))
        .consumed(book.getConsumed())
        .dateAdded(book.getDateAdded()).build();
  }
}
