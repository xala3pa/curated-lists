package com.xala3pa.springbootmvc.books.controller;

import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.boundary.SaveBook;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.inputData.BookInputData;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.books.exception.ErrorResponse;
import com.xala3pa.springbootmvc.books.exception.NotFoundException;
import java.util.Collection;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  private FindBookListByAuthor findBookListByAuthor;
  private FindBookByIsbn findBookByIsbn;
  private FindAllBooks findAllBooks;
  private SaveBook saveBook;

  public BookController(FindBookListByAuthor findBookListByAuthor,
      FindBookByIsbn findBookByIsbn, FindAllBooks findAllBooks, SaveBook saveBook) {
    this.findBookListByAuthor = findBookListByAuthor;
    this.findBookByIsbn = findBookByIsbn;
    this.findAllBooks = findAllBooks;
    this.saveBook = saveBook;
  }

  @GetMapping("/books")
  public Collection<BookOutputData> booksByAuthor(
      @RequestParam(value = "author") Optional<String> author) {
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

  @GetMapping("/{isbn}/book")
  public ResponseEntity<BookOutputData> booksByIsbn(@PathVariable Long isbn) {
    LOGGER.info("Retrieving Book by ISBN: {}", isbn);
    try {
      BookOutputData bookOutputData = findBookByIsbn
          .getBook(BookByIsbnInputData.builder().isbn(isbn).build());

      return Optional.ofNullable(bookOutputData)
          .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
          .orElseThrow(NotFoundException::new);
    } catch (BooksNotFoundException exception) {
      LOGGER.info("Book with ISBN {}, not found", isbn);
      throw new NotFoundException();
    }
  }

  @PostMapping("/{isbn}/book")
  public ResponseEntity<?> saveBook(@PathVariable Long isbn,
      @Valid @RequestBody BookInputData bookInputData) {

    bookInputData.setIsbn(isbn);
    return new ResponseEntity<>(saveBook.save(bookInputData).mapToBookOutputData(),
        HttpStatus.CREATED);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleException(MethodArgumentNotValidException exception) {

    String errorMsg = exception.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .findFirst()
        .orElse(exception.getMessage());

    return ErrorResponse.builder().message(errorMsg).build();
  }
}
