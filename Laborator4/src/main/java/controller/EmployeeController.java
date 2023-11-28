package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.builder.BookBuilder;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.EmployeeView;

import java.time.LocalDate;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final BookService bookService;

    public EmployeeController(EmployeeView employeeView, BookServiceImpl bookService) {
        this.employeeView = employeeView;
        this.bookService = bookService;

        this.employeeView.addViewAllBooksButtonListener(new EmployeeController.ViewAllBooksListener());
        this.employeeView.addIntroduceABookButtonListener(new EmployeeController.IntroduceABookListener());
        this.employeeView.addUpdateBookButtonListener(new EmployeeController.UpdateABookListener());
        this.employeeView.addDeleteBookButtonListener(new EmployeeController.DeleteABookListener());
        this.employeeView.addConfirmUpdateButtonListener(new EmployeeController.ConfirmUpdateListener());
        this.employeeView.addConfirmIntroduceButtonListener(new EmployeeController.ConfirmIntroduceListener());
    }

    private class ViewAllBooksListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            refreshBooksTable();
        }
    }
    private void refreshBooksTable() {
        List<Book> books = bookService.findAll();
        ObservableList<Book> observableBooks = FXCollections.observableArrayList(books);
        employeeView.displayBooks(observableBooks);
    }

    private class IntroduceABookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            employeeView.openIntroduceABookWindow();
        }
    }

    private class UpdateABookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            employeeView.openUpdateABookWindow();
        }
    }

    private class DeleteABookListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Book selectedBook = employeeView.getSelectedBook();
            if (selectedBook != null) {
                try{
                    if(bookService.deleteBook(selectedBook.getId()))
                    {
                        refreshBooksTable();
                        employeeView.displayMessage("The book has been deleted successfully!");
                    } else {
                        employeeView.displayError("The book could not be deleted!");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    employeeView.displayError("The book could not be deleted!");
                }
            } else {
                employeeView.displayError("You must select a book to delete first!");
            }
        }
    }

    private class ConfirmUpdateListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Book selectedBook = employeeView.getSelectedBook();
            if (selectedBook != null) {
                String title = employeeView.getTitleField().getText();
                String author = employeeView.getAuthorField().getText();
                LocalDate date = LocalDate.parse(employeeView.getPublishedDateField().getText());
                double price = Double.parseDouble(employeeView.getPriceField().getText());
                int stock = Integer.parseInt(employeeView.getStockField().getText());
                try{
                    if(bookService.updateBook(selectedBook.getId(), title, author, date, price, stock)){
                        refreshBooksTable();
                        employeeView.displayMessage("The book has been updated successfully!");
                    } else {
                        employeeView.displayError("The book was not updated!");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    employeeView.displayError("The order was not updated!");
                }
            } else {
                employeeView.displayError("You must select a book to update first!");
            }
        }
    }

    private class ConfirmIntroduceListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = employeeView.getTitleField().getText();
            String author = employeeView.getAuthorField().getText();
            LocalDate date = LocalDate.parse(employeeView.getPublishedDateField().getText());
            double price = Double.parseDouble(employeeView.getPriceField().getText());
            int stock = Integer.parseInt(employeeView.getStockField().getText());
            try{
                Book book = new BookBuilder()
                        .setTitle(title)
                        .setAuthor(author)
                        .setPublishedDate(date)
                        .setPrice(price)
                        .setStock(stock)
                        .build();
                if(bookService.save(book)){
                    refreshBooksTable();
                    employeeView.displayMessage("The book has been added successfully!");
                } else {
                    employeeView.displayError("The book was not added!");
                }
            } catch (Exception e){
                e.printStackTrace();
                employeeView.displayError("The order was not added!");
            }
        }
    }

}