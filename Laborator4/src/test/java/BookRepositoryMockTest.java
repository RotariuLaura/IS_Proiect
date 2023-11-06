import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.book.BookRepositoryMock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMockTest {
    private BookRepositoryMock bookRepository;
    @BeforeEach
    public void setUp(){
        bookRepository = new BookRepositoryMock();
    }
    @Test
    public void testFindAll(){
        Book book = new BookBuilder()
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        Book book2 = new BookBuilder()
                .setTitle("The Christmas Holiday")
                .setAuthor("Phillipa Ashley")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        bookRepository.save(book);
        bookRepository.save(book2);
        List<Book> books = bookRepository.findAll();
        assertEquals(books.get(0), book);
        assertEquals(books.get(1), book2);
        assertEquals(2, books.size());
    }
    @Test
    public void testFindById(){
        Book book = new BookBuilder()
                .setId(1L)
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        bookRepository.save(book);
        Optional <Book> foundBook = bookRepository.findById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals(book, foundBook.get());
    }
    @Test
    public void testSave(){
        Book book = new BookBuilder()
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        assertTrue(bookRepository.save(book));
        List <Book> books = bookRepository.findAll();
        assertEquals(book, books.get(0));
        assertEquals(1, books.size());
    }
    @Test
    public void testRemoveAll(){
        Book book = new BookBuilder()
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        bookRepository.save(book);
        bookRepository.removeAll();
        List <Book> books = bookRepository.findAll();
        assertTrue(books.isEmpty());
    }

}
