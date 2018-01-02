package com.xala3pa.springbootmvc.books.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xala3pa.books.boundary.BookListByAuthor;
import com.xala3pa.books.inputData.BookListByAuthorInputData;
import com.xala3pa.books.outputData.BookOutputData;
import com.xala3pa.springbootmvc.MVCConfig;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ContextConfiguration(classes = {MVCConfig.class})
public class BookControllerTest {

  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String BOOK_URL_TEMPLATE = "/book?author=Robert C. Martin";
  private static final String BOOK_TITLE = "Clean Architecture";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookListByAuthor bookListByAuthor;

  @Test
  public void booksByAuthor() throws Exception {
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
}