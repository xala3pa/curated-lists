package com.xala3pa.books.boundary;

import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.List;

@FunctionalInterface
public interface BookListByAuthor {

  List<BookOutputData> getBooks(BookListByAuthorInputData bookListByAuthorInputData);
}
