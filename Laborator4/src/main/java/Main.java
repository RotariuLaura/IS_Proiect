import database.JDBCConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySql;

import java.time.LocalDate;


public class Main {
    public static void main(String [] args){
        JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper("test_library");
        BookRepository bookRepository = new BookRepositoryMySql(connectionWrapper.getConnection());

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
        bookRepository.save(book);
        bookRepository.save(book2);
        System.out.println(bookRepository.findAll());
        System.out.println(bookRepository.findById(1L));
        bookRepository.removeAll();
        System.out.println(bookRepository.findAll());
        bookRepository.save(book);
        bookRepository.save(book2);
        System.out.println(bookRepository.findAll());
    }
}
