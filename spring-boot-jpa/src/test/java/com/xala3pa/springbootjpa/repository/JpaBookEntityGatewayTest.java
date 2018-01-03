package com.xala3pa.springbootjpa.repository;

import com.xala3pa.books.Book;
import com.xala3pa.springbootjpa.books.JpaBookEntityGateway;
import com.xala3pa.springbootjpa.RepositoryConfig;
import com.xala3pa.springbootjpa.books.entity.BookEntity;
import com.xala3pa.springbootjpa.books.entity.BookEntityCategory;
import com.xala3pa.springbootjpa.books.entity.BookEntityStatus;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class})
@DataJpaTest
public class JpaBookEntityGatewayTest {

  private static final long ISBN = 9780134494166L;
  private static final String ROBERT_C_MARTIN = "Robert C. Martin";
  private static final String CLEAN_ARCHITECTURE = "Clean Architecture";

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private JpaBookEntityGateway jpaBookEntityGateway;

  private BookEntity cleanArchitectureBook;

  @Before
  public void setUp() {
    cleanArchitectureBook = BookEntity.builder()
        .title(CLEAN_ARCHITECTURE)
        .isbn(ISBN)
        .author(ROBERT_C_MARTIN)
        .description("Clean Architecture, a Craftsman's Guide to Software Structure and Design")
        .bookCategory(BookEntityCategory.TECHNICAL)
        .bookStatus(BookEntityStatus.READ)
        .build();

    testEntityManager.persist(cleanArchitectureBook);
  }

  @Test
  public void testFindByAuthor() {

    List<Book> uncleBobBooks = jpaBookEntityGateway.findByAuthor(ROBERT_C_MARTIN).get();

    assertThat(uncleBobBooks.size()).isNotZero();
    assertThat(uncleBobBooks.get(0).getIsbn()).isEqualTo(ISBN);
  }

  @Test
  public void testGetBookByIsbn() {

    Optional<Book> uncleBobBook = jpaBookEntityGateway.getBookByIsbn(ISBN);

    assertThat(uncleBobBook.get())
        .isEqualToComparingFieldByField(cleanArchitectureBook.mapToBook());
  }
}
