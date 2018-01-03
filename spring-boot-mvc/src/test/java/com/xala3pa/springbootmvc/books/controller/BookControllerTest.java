package com.xala3pa.springbootmvc.books.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xala3pa.books.boundary.BookListByAuthor;
import com.xala3pa.books.exception.BooksNotFoundException;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootjpa.RepositoryConfig;
import com.xala3pa.springbootmvc.MVCConfig;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {MVCConfig.class})
public class BookControllerTest {

  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String BOOK_URL_TEMPLATE = "/books?author=Robert C. Martin";
  private static final String BOOK_TITLE = "Clean Architecture";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookListByAuthor bookListByAuthor;

  @Test
  public void should_return_books_by_author() throws Exception {
    BookListByAuthorInputData books = BookListByAuthorInputData.builder().author(ROBERT_C_MARTIN)
        .build();

    BookOutputData bookOutputData = BookOutputData.builder()
        .author(ROBERT_C_MARTIN)
        .title(BOOK_TITLE).build();

    List<BookOutputData> bookOutputDataList = Collections.singletonList(bookOutputData);

    when(bookListByAuthor.getBooks(books)).thenReturn(bookOutputDataList);

    this.mockMvc.perform(get(BOOK_URL_TEMPLATE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value(BOOK_TITLE));
  }

  @Test
  public void should_return_BooksNotFoundException_when_no_books() throws Exception {

    when(bookListByAuthor.getBooks(any(BookListByAuthorInputData.class)))
        .thenThrow(new BooksNotFoundException());

    this.mockMvc.perform(get(BOOK_URL_TEMPLATE))
        .andExpect(status().isNotFound());
  }
}