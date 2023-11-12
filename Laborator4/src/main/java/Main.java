import database.JDBCConnectionWrapper;
import model.AudioBook;
import model.Book;
import model.EBook;
import model.builder.AudioBookBuilder;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;
import repository.book.*;
import service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class Main {
    public static void main(String [] args){
        JDBCConnectionWrapper connectionWrapper = new JDBCConnectionWrapper("test_library");


        BookRepository bookRepository = new BookRepositoryCacheDecorator(
                new BookRepositoryMySql(connectionWrapper.getConnection()),
                new Cache<>());
        EBookRepository eBookRepository = new EBookRepositoryCacheDecorator(
                new EBookRepositoryMySql(connectionWrapper.getConnection()),
                new Cache<>());
        AudioBookRepository audioBookRepository = new AudioBookRepositoryCacheDecorator(
                new AudioBookRepositoryMySql(connectionWrapper.getConnection()),
                new Cache<>());

        BookService bookService = new BookServiceImpl(bookRepository);
        EBookService eBookService = new EBookServiceImpl(eBookRepository);
        AudioBookService audioBookService = new AudioBookServiceImpl(audioBookRepository);

        Book book = new BookBuilder()
                .setTitle("Pinocchio")
                .setAuthor("Carlo Colodi")
                .setPublishedDate(LocalDate.of(2012, 6,2))
                .build();
        Book book2 = new BookBuilder()
                .setTitle("The Christmas Holiday")
                .setAuthor("Phillipa Ashley")
                .setPublishedDate(LocalDate.of(2020, 10,6))
                .build();
        //bookRepository.removeAll();
        bookService.save(book);
        bookService.save(book2);
        System.out.println(bookService.findAll());
        System.out.println(bookService.findById(1L));
        System.out.println(bookService.getAgeOfBook(1L));

        AudioBook book3 = new AudioBookBuilder()
                .setTitle("The Christmas Holiday")
                .setAuthor("Phillipa Ashley")
                .setPublishedDate(LocalDate.of(2020, 10,6))
                .setRunTime(60)
                .build();
        //audioBookRepository.removeAllAudioBooks();
        audioBookService.saveAudioBook(book3);
        System.out.println(audioBookService.findAllAudioBooks());

        //eBookRepository.removeAllEBooks();
        EBook book4 = new EBookBuilder()
        .setTitle("The Christmas Holiday")
        .setAuthor("Phillipa Ashley")
        .setPublishedDate(LocalDate.of(2020, 10,6))
        .setFormat("PDF")
        .build();
        eBookService.saveEBook(book4);
        System.out.println(eBookService.findAllEBooks());
    }
}
