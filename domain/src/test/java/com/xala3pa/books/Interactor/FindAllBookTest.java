package com.xala3pa.books.Interactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.outputData.BookOutputData;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FindAllBookTest {

  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";
  private static final long ISBN_CLEAN_ARCH = 9780134494166L;
  private static final String CLEAN_CODE = "Clean code";
  private static final Long ISBN_CLEAN_CODE = 1973618361543L;
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";

  private Book cleanArchitectureBook;
  private Book cleanCodeBook;

  @Mock
  private BookGateway bookGateway;

  @InjectMocks
  private FindAllBooksInteractor findAllBooksInteractor = new FindAllBooksInteractor(bookGateway);

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    cleanArchitectureBook = Book.builder()
        .title(CLEAN_ARCHITECTURE)
        .isbn(ISBN_CLEAN_ARCH)
        .author(ROBERT_C_MARTIN)
        .description("Clean Architecture, a Craftsman's Guide to Software Structure and Design")
        .bookCategory(BookCategory.TECHNICAL)
        .bookStatus(BookStatus.READING)
        .build();

    cleanCodeBook = Book.builder()
        .title(CLEAN_CODE)
        .isbn(ISBN_CLEAN_CODE)
        .author(ROBERT_C_MARTIN)
        .description("Clean Code, a handbook of Agile Software Craftsmanship")
        .bookCategory(BookCategory.TECHNICAL)
        .bookStatus(BookStatus.READ)
        .build();
  }

  @Test
  public void should_return_all_books() {

    Optional<List<Book>> books = Optional.of(Arrays.asList(cleanArchitectureBook, cleanCodeBook));

    when(bookGateway.findAll()).thenReturn(books);

    List<BookOutputData> bookOutputData = findAllBooksInteractor.getBooks();

    assertThat(bookOutputData.size()).isEqualTo(2);
  }
}