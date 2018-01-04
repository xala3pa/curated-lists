package com.xala3pa.books.Interactor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import com.xala3pa.books.gateway.BookGateway;
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
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class FindBookListsByAuthorInteractorTest {

  private static final long ISBN = 9780134494166L;
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";

  private Book cleanArchitectureBook;

  @Mock
  private BookGateway bookGateway;

  @InjectMocks
  private FindBookListsByAuthorInteractor getBookListsByAuthor = new FindBookListsByAuthorInteractor(bookGateway);

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
  public void should_return_list_of_books_by_author() {

    BookListByAuthorInputData bookListByAuthorInputData = BookListByAuthorInputData.builder()
        .author(ROBERT_C_MARTIN)
        .build();

    Optional<List<Book>> books = Optional.of(Collections.singletonList(cleanArchitectureBook));

    when(bookGateway.findByAuthor(any(String.class))).thenReturn(books);

    List<BookOutputData> bookOutputData =  getBookListsByAuthor.getBooks(bookListByAuthorInputData);

    assertThat(bookOutputData.size()).isNotZero();
    assertThat(bookOutputData.get(0).getAuthor()).isEqualTo(ROBERT_C_MARTIN);
  }
}