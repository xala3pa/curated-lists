package com.xala3pa.books.Interactor;

import com.xala3pa.books.Book;
import com.xala3pa.books.boundary.SaveBook;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.inputData.BookInputData;

public class SaveBookInteractor implements SaveBook {

  private BookGateway bookGateway;

  public SaveBookInteractor(BookGateway bookGateway) {
    this.bookGateway = bookGateway;
  }

  @Override
  public Book save(BookInputData bookInputData) {
    return bookGateway.save(bookInputData.toBook());
  }
}
