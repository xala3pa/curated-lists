package com.xala3pa.books;

import com.xala3pa.books.outputData.BookOutputData;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  private Long isbn;
  private String title;
  private String description;
  private BookCategory bookCategory;
  private BookStatus bookStatus;
  private String author;
  private Date dateAdded;
  private Date consumed;

  public BookOutputData mapToBookOutputData() {
    return BookOutputData.builder()
        .isbn(getIsbn())
        .title(getTitle())
        .author(getAuthor())
        .description(getDescription())
        .bookCategory(BookCategory.valueOf(getBookCategory().name()))
        .bookStatus(BookStatus.valueOf(getBookStatus().name()))
        .consumed(getConsumed())
        .dateAdded(getDateAdded()).build();
  }
}

