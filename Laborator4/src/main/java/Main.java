import database.JDBCConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySql;
import repository.book.Cache;
import service.BookService;
import service.BookServiceImpl;

import java.time.LocalDate;


public class Main {
    public static void main(String [] args){
        JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper("test_library");

        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySql(connectionWrapper.getConnection()),
                new Cache<>());
        BookService bookService = new BookServiceImpl(bookRepository);

        Book book = new BookBuilder()
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        Book book2 = new BookBuilder()
                .setTitle("The Christmas Holiday")
                .setAuthor("Phillipa Ashley")
                .setPublishedDate(LocalDate.of(2020, 10,6))
                .build();
        bookService.save(book);
        bookService.save(book2);
        System.out.println(bookService.findAll());
        System.out.println(bookService.findById(1L));
    }
}
