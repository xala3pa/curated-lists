package com.xala3pa.books.Interactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import com.xala3pa.books.gateway.BookGateway;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
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
public class FindBookByIsbnInteractorTest {

  private static final long ISBN = 9780134494166L;
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";

  private Book cleanArchitectureBook;

  @Mock
  private BookGateway bookGateway;

  @InjectMocks
  private FindBookByIsbnInteractor findBookByIsbnInteractor = new FindBookByIsbnInteractor(
      bookGateway);

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    cleanArchitectureBook = Book.builder()
        .title(CLEAN_ARCHITECTURE)
        .isbn(ISBN)
        .author(ROBERT_C_MARTIN)
        .description("Clean Architecture, a Craftsman's Guide to Software Structure and Design")
        .bookCategory(BookCategory.TECHNICAL)
        .bookStatus(BookStatus.READ)
        .build();
  }

  @Test
  public void should_return_a_book_when_we_search_by_isbn() throws Exception {

    BookByIsbnInputData bookByIsbnInputData = BookByIsbnInputData.builder()
        .isbn(ISBN)
        .build();

    Optional<Book> book = Optional.of(cleanArchitectureBook);

        when(bookGateway.getBookByIsbn(any(Long.class))).thenReturn(book);

    BookOutputData bookOutputData = findBookByIsbnInteractor.getBook(bookByIsbnInputData);

    assertThat(bookOutputData).isEqualToComparingFieldByField(cleanArchitectureBook.mapToBookOutputData());
  }
}