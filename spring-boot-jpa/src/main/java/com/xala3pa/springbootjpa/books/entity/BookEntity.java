package com.xala3pa.springbootjpa.books.entity;

import com.xala3pa.books.Book;
import com.xala3pa.books.BookCategory;
import com.xala3pa.books.BookStatus;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long isbn;
  private String title;
  private String description;
  private BookEntityCategory bookCategory;
  private BookEntityStatus bookStatus;
  private String author;
  private Date dateAdded;
  private Date consumed;

  public Book mapToBook() {
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
