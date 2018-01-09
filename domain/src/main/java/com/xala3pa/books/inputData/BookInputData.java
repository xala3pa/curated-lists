package com.xala3pa.books.inputData;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInputData {
  @NotNull(message = "ISBN cannot be an empty field!")
  private Long isbn;
  @NotBlank(message = "Title cannot be an empty field!")
  private String title;
  private String description;
  private BookCategory bookCategory;
  private BookStatus bookStatus;
  @NotBlank(message = "Author cannot be an empty field!")
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
