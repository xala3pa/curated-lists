package com.xala3pa.books.boundary;

import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.outputData.BookOutputData;

@FunctionalInterface
public interface BookByIsbn {

  BookOutputData getBook(BookByIsbnInputData bookByIsbnInputData);
}
