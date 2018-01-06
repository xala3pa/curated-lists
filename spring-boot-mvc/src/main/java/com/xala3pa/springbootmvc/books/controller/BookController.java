package com.xala3pa.springbootmvc.books.controller;

import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.books.exception.NotFoundException;
import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  private FindBookListByAuthor findBookListByAuthor;
  private FindBookByIsbn findBookByIsbn;
  private FindAllBooks findAllBooks;

  public BookController(FindBookListByAuthor findBookListByAuthor,
      FindBookByIsbn findBookByIsbn, FindAllBooks findAllBooks) {
    this.findBookListByAuthor = findBookListByAuthor;
    this.findBookByIsbn = findBookByIsbn;
    this.findAllBooks = findAllBooks;
  }

  @GetMapping(value = "/books")
  public Collection<BookOutputData> booksByAuthor(@RequestParam(value = "author") Optional<String> author) {
    try {
      if (!author.isPresent()) {
        LOGGER.info("Retrieving All Books...");
        return findAllBooks.getBooks();
      }

      LOGGER.info("Retrieving Books of: {}", author);
      return findBookListByAuthor
          .getBooks(BookListByAuthorInputData.builder().author(author.get()).build());
    } catch (BooksNotFoundException exception) {
      LOGGER.info("Books not found", author);
      throw new NotFoundException();
    }
  }

  @GetMapping(value = "/{isbn}/book")
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
