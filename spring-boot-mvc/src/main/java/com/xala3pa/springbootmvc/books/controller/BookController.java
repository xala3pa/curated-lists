package com.xala3pa.springbootmvc.books.controller;

import com.xala3pa.books.boundary.BookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.books.exception.NotFoundException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  private BookListByAuthor bookListByAuthor;

  public BookController(BookListByAuthor bookListByAuthor) {
    this.bookListByAuthor = bookListByAuthor;
  }

  @RequestMapping(method = RequestMethod.GET)
  public Collection<BookOutputData> booksByAuthor(@RequestParam(value = "author") String author) {
    LOGGER.info("Retrieving Books of: {}", author);
    try {
      return bookListByAuthor.getBooks(BookListByAuthorInputData.builder().author(author).build());
    } catch (BooksNotFoundException exception) {
      LOGGER.info("Books of {} not found", author);
      throw new NotFoundException();
    }
  }
}
