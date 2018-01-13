package com.xala3pa.springbootmvc.books.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import com.xala3pa.books.boundary.FindAllBooks;
import com.xala3pa.books.boundary.FindBookByIsbn;
import com.xala3pa.books.boundary.FindBookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookByIsbnInputData;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.MVCConfig;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {MVCConfig.class})
public class BookControllerTest {

  private static final String BOOK_BY_AUTHOR_URL_TEMPLATE = "/books?author=Robert C. Martin";
  private static final String BOOK_BY_ISBN_URL_TEMPLATE = "/9780134494166/book";
  private static final String BOOK_SAVE_BY_ISBN_URL_TEMPLATE = "/{isbn}/book";
  private static final String BOOKS_URL_TEMPLATE = "/books";
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";
  private static final long ISBN_CLEAN_ARCH = 9780134494166L;
  private static final String CLEAN_CODE = "Clean code";
  private static final Long ISBN_CLEAN_CODE = 1973618361543L;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FindBookListByAuthor findBookListByAuthor;

  @MockBean
  private FindBookByIsbn findBookByIsbn;

  @MockBean
  private FindAllBooks findAllBooks;

  private BookOutputData cleanArchitectureBook = BookOutputData.builder()
      .title(CLEAN_ARCHITECTURE)
      .isbn(ISBN_CLEAN_ARCH)
      .author(ROBERT_C_MARTIN)
      .description("Clean Architecture, a Craftsman's Guide to Software Structure and Design")
      .bookCategory(BookCategory.TECHNICAL)
      .bookStatus(BookStatus.READING)
      .build();

  private BookOutputData cleanCodeBook = BookOutputData.builder()
      .title(CLEAN_CODE)
      .isbn(ISBN_CLEAN_CODE)
      .author(ROBERT_C_MARTIN)
      .description("Clean Code, a handbook of Agile Software Craftsmanship")
      .bookCategory(BookCategory.TECHNICAL)
      .bookStatus(BookStatus.READ)
      .build();

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void should_return_books_by_author() throws Exception {
    BookListByAuthorInputData books = BookListByAuthorInputData.builder().author(ROBERT_C_MARTIN)
        .build();

    List<BookOutputData> bookOutputDataList = Collections.singletonList(cleanArchitectureBook);

    when(findBookListByAuthor.getBooks(books)).thenReturn(bookOutputDataList);

    this.mockMvc.perform(get(BOOK_BY_AUTHOR_URL_TEMPLATE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value(CLEAN_ARCHITECTURE));
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
    BookByIsbnInputData book = BookByIsbnInputData.builder().isbn(ISBN_CLEAN_ARCH).build();

    when(findBookByIsbn.getBook(book)).thenReturn(cleanArchitectureBook);

    this.mockMvc.perform(get(BOOK_BY_ISBN_URL_TEMPLATE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value(CLEAN_ARCHITECTURE))
        .andExpect(jsonPath("$.isbn").value(ISBN_CLEAN_ARCH));
  }

  @Test
  public void should_return_BooksNotFoundException_when_no_books_found_by_isbn() throws Exception {

    when(findBookByIsbn.getBook(any(BookByIsbnInputData.class)))
        .thenThrow(new BooksNotFoundException());

    this.mockMvc.perform(get(BOOK_BY_ISBN_URL_TEMPLATE))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_return_all_books() throws Exception {

    when(findAllBooks.getBooks()).thenReturn(Arrays.asList(cleanArchitectureBook, cleanCodeBook));

    this.mockMvc.perform(get(BOOKS_URL_TEMPLATE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void should_return_BooksNotFoundException_when_no_books_found() throws Exception {

    when(findAllBooks.getBooks())
        .thenThrow(new BooksNotFoundException());

    this.mockMvc.perform(get(BOOKS_URL_TEMPLATE))
        .andExpect(status().isNotFound());
  }

  @Test
  public void should_save_a_book() throws Exception {

    this.mockMvc.perform(post(BOOK_SAVE_BY_ISBN_URL_TEMPLATE, ISBN_CLEAN_ARCH)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(cleanArchitectureBook)))
        .andExpect(jsonPath("$.title").value(CLEAN_ARCHITECTURE))
        .andExpect(jsonPath("$.isbn").value(ISBN_CLEAN_ARCH))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  public void should_return_an_exception_saving_a_book_with_a_bad_request() throws Exception {

    this.mockMvc.perform(post(BOOK_SAVE_BY_ISBN_URL_TEMPLATE, ISBN_CLEAN_ARCH)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{'isbn':,121212131212312}"))
        .andExpect(status().isBadRequest());
  }
}