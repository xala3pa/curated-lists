package com.xala3pa.books.Boundary;

import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.List;

@FunctionalInterface
public interface GetBookListByAuthor {

  List<BookOutputData> getBookListByAuthor(BookListByAuthorInputData bookListByAuthorInputData);
}
