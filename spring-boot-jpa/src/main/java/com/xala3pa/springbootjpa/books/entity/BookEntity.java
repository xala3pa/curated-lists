package com.xala3pa.springbootjpa.books.entity;

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
}
