package com.xala3pa.springbootmvc.books.controller;

import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.books.exception.NotFoundException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  private FindBookListByAuthor findBookListByAuthor;
  private FindBookByIsbn findBookByIsbn;

  public BookController(FindBookListByAuthor findBookListByAuthor,
      FindBookByIsbn findBookByIsbn) {
    this.findBookListByAuthor = findBookListByAuthor;
    this.findBookByIsbn = findBookByIsbn;
  }

  @RequestMapping(value = "/books", method = RequestMethod.GET)
  public Collection<BookOutputData> booksByAuthor(@RequestParam(value = "author") String author) {
    LOGGER.info("Retrieving Books of: {}", author);
    try {
      return findBookListByAuthor.getBooks(BookListByAuthorInputData.builder().author(author).build());
    } catch (BooksNotFoundException exception) {
      LOGGER.info("Books of {} not found", author);
      throw new NotFoundException();
    }
  }

  @RequestMapping(value = "/{isbn}/book", method = RequestMethod.GET)
  public BookOutputData booksByIsbn(@PathVariable Long isbn) {
    LOGGER.info("Retrieving Book by ISBN: {}", isbn);
    try {
      return findBookByIsbn.getBook(BookByIsbnInputData.builder().isbn(isbn).build());
    } catch (BooksNotFoundException exception) {
      LOGGER.info("Book with ISBN {}, not found", isbn);
      throw new NotFoundException();
    }
  }
}
