import database.JDBCConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySqlTest {
    JDBCConnectionWrapper connectionWrapper;
    BookRepository bookRepository;
    @BeforeEach
    public void setUp(){
        this.connectionWrapper = new JDBCConnectionWrapper("test_library");
        this.bookRepository = new BookRepositoryMySql(connectionWrapper.getConnection());
    }
    @Test
    public void testFindAll(){
        List <Book> books = bookRepository.findAll();
        assertNotNull(books);
    }
    @Test
    public void testFindById(){
        Optional <Book> book = bookRepository.findById(1L);
        assertTrue(book.isPresent());
    }
    @Test
    public void testSave(){
        Book book = new BookBuilder()
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        assertTrue(bookRepository.save(book));
    }
    @Test
    public void testRemoveAll(){
        bookRepository.removeAll();
        List <Book> books = bookRepository.findAll();
        assertTrue(books.isEmpty());
    }
}
