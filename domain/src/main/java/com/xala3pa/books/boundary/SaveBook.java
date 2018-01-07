package com.xala3pa.books.boundary;

import com.xala3pa.books.Book;
import com.xala3pa.books.inputData.BookInputData;

@FunctionalInterface
public interface SaveBook {
  Book save(BookInputData bookInputData);

}
