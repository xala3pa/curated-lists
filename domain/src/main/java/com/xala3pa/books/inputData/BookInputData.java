package com.xala3pa.books.inputData;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInputData {
  private Long isbn;
  private String title;
  private String description;
  private BookCategory bookCategory;
  private BookStatus bookStatus;
  private String author;
  private Date dateAdded;
  private Date consumed;

  public Book toBook() {
    return Book.builder()
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
