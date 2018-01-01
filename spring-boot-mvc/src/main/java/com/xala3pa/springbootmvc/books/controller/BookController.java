package com.xala3pa.springbootmvc.books.controller;

import com.xala3pa.books.Boundary.BookListByAuthor;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.Collection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

  private BookListByAuthor bookListByAuthor;

  public BookController(BookListByAuthor bookListByAuthor) {
    this.bookListByAuthor = bookListByAuthor;
  }

  @RequestMapping(method = RequestMethod.GET)
  public Collection<BookOutputData> booksByAuthor(@RequestParam(value = "author") String author ){
    return bookListByAuthor.getBooks(BookListByAuthorInputData.builder().author(author).build());
  }
}
