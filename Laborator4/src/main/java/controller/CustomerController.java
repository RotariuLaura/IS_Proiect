package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.CustomerView;

import java.util.List;

public class CustomerController {

    private final CustomerView customerView;
    private final BookService bookService;

    public CustomerController(CustomerView customerView, BookServiceImpl bookService) {
        this.customerView = customerView;
        this.bookService = bookService;

        this.customerView.addViewAllBooksButtonListener(new CustomerController.ViewAllBooksListener());
        this.customerView.addBuyABookButtonListener(new CustomerController.BuyABookListener());
    }

    private class ViewAllBooksListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            List <Book> books = bookService.findAll();
            ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);
            customerView.displayBooks(observableBooks);
        }
    }

    private class BuyABookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

        }
    }

}