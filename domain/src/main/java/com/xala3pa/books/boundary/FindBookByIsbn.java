package com.xala3pa.books.boundary;

import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.outputData.BookOutputData;

@FunctionalInterface
public interface FindBookByIsbn {

  BookOutputData getBook(BookByIsbnInputData bookByIsbnInputData);
}
