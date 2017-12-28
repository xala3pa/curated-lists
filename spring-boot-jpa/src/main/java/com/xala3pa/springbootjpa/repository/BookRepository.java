package com.xala3pa.springbootjpa.repository;

import com.xala3pa.springbootjpa.entity.BookEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

  List<BookEntity> findByAuthor(String author);

  BookEntity getBookByIsbn(String isbn);
}
