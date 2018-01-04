package com.xala3pa.springbootmvc.books.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.MVCConfig;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {MVCConfig.class})
public class BookControllerTest {

  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String BOOK_BY_AUTHOR_URL_TEMPLATE = "/books?author=Robert C. Martin";
  private static final String BOOK_BY_ISBN_URL_TEMPLATE = "/1213174625386/book";
  private static final String BOOK_TITLE = "Clean Architecture";
  private static final long ISBN = 1213174625386L;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FindBookListByAuthor findBookListByAuthor;

  @MockBean
  private FindBookByIsbn findBookByIsbn;

  private BookOutputData bookOutputData = BookOutputData.builder()
      .author(ROBERT_C_MARTIN)
      .title(BOOK_TITLE)
      .isbn(ISBN).build();

  @Test
  public void should_return_books_by_author() throws Exception {
    BookListByAuthorInputData books = BookListByAuthorInputData.builder().author(ROBERT_C_MARTIN)
        .build();

    List<BookOutputData> bookOutputDataList = Collections.singletonList(bookOutputData);

    when(findBookListByAuthor.getBooks(books)).thenReturn(bookOutputDataList);

    this.mockMvc.perform(get(BOOK_BY_AUTHOR_URL_TEMPLATE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value(BOOK_TITLE));
  }

  @Test
  public void should_return_BooksNotFoundException_when_no_books_found_by_author()
      throws Exception {

    when(findBookListByAuthor.getBooks(any(BookListByAuthorInputData.class)))
        .thenThrow(new BooksNotFoundException());

    this.mockMvc.perform(get(BOOK_BY_AUTHOR_URL_TEMPLATE))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_return_book_by_isbn() throws Exception {
    BookByIsbnInputData book = BookByIsbnInputData.builder().isbn(ISBN).build();

    when(findBookByIsbn.getBook(book)).thenReturn(bookOutputData);

    this.mockMvc.perform(get(BOOK_BY_ISBN_URL_TEMPLATE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value(BOOK_TITLE))
        .andExpect(jsonPath("$.isbn").value(ISBN));
  }

  @Test
  public void should_return_BooksNotFoundException_when_no_books_found_by_isbn() throws Exception {

    when(findBookByIsbn.getBook(any(BookByIsbnInputData.class)))
        .thenThrow(new BooksNotFoundException());

    this.mockMvc.perform(get(BOOK_BY_ISBN_URL_TEMPLATE))
        .andExpect(status().isNotFound());
  }
}