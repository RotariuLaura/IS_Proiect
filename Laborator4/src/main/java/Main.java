import database.JDBCConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryMock;
import repository.book.BookRepositoryMySql;

import java.sql.Connection;
import java.time.LocalDate;


public class Main {
    public static void main(String [] args){
        JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper("test_library");
        BookRepository bookRepository = new BookRepositoryMySql(connectionWrapper.getConnection());

        Book book = new BookBuilder()
                .setId(2020L)
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2010, 6,2))
                .build();
        bookRepository.save(book);
        System.out.println(bookRepository.findAll());
    }
}
