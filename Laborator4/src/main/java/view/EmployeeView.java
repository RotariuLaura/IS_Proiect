package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Book;

import java.time.LocalDate;

public class EmployeeView {

    private Button viewBooksButton;
    private Button introduceBookButton;
    private Button updateBookButton;
    private Button deleteBookButton;
    private TableView<Book> booksTable;
    private Button confirmUpdateButton;
    private Button confirmIntroduceButton;
    private Book selectedBook;
    private Spinner<Integer> quantitySpinner;
    TextField titleField;
    TextField authorField;
    TextField publishedDateField;
    TextField priceField;
    TextField stockField;

    public EmployeeView(Stage primaryStage) {
        primaryStage.setTitle("Employees");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        initializeSceneTitle(gridPane);

        initializeFields(gridPane);

        initializeBooksTable(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeSceneTitle(GridPane gridPane){
        Text sceneTitle = new Text("Welcome to our Book Store");
        sceneTitle.setFont(Font.font("Tahome", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);
    }

    private void initializeFields(GridPane gridPane){

        HBox buttonsRow = new HBox(10);
        buttonsRow.setAlignment(Pos.BOTTOM_CENTER);

        introduceBookButton = new Button("Introduce book");
        viewBooksButton = new Button("View all books");
        updateBookButton = new Button("Update book");
        deleteBookButton = new Button("Delete book");

        buttonsRow.getChildren().addAll(introduceBookButton, viewBooksButton, updateBookButton, deleteBookButton);

        gridPane.add(buttonsRow, 0, 1, 2, 1);

        confirmUpdateButton = new Button("Confirm Update");
        confirmIntroduceButton = new Button("Confirm");
    }

    public void initializeBooksTable(GridPane gridPane){
        booksTable = new TableView<>();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setMinWidth(120);

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setMinWidth(120);

        TableColumn<Book, LocalDate> publishedDateColumn = new TableColumn<>("Published Date");
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));
        publishedDateColumn.setMinWidth(120);

        TableColumn<Book, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setMinWidth(120);

        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setMinWidth(120);

        booksTable.getColumns().addAll(titleColumn, authorColumn, publishedDateColumn, priceColumn, stockColumn);
        VBox booksTableBox = new VBox(booksTable);
        booksTableBox.setAlignment(Pos.CENTER);
        booksTableBox.setPadding(new Insets(20));

        gridPane.add(booksTableBox, 0, 2, 2, 1);
    }

    public void addViewAllBooksButtonListener(EventHandler<ActionEvent> viewBooksButtonListener) {
        viewBooksButton.setOnAction(viewBooksButtonListener);
    }

    public void addIntroduceABookButtonListener(EventHandler<ActionEvent> introduceBookButtonListener) {
        introduceBookButton.setOnAction(introduceBookButtonListener);
    }

    public void addUpdateBookButtonListener(EventHandler<ActionEvent> updateBookButtonListener) {
        updateBookButton.setOnAction(updateBookButtonListener);
    }

    public void addDeleteBookButtonListener(EventHandler<ActionEvent> deleteBookButtonListener) {
        deleteBookButton.setOnAction(deleteBookButtonListener);
    }
    public void addConfirmUpdateButtonListener(EventHandler<ActionEvent> confirmUpdateListener) {
        confirmUpdateButton.setOnAction(confirmUpdateListener);
    }

    public void addConfirmIntroduceButtonListener(EventHandler<ActionEvent> confirmIntroduceListener) {
        confirmIntroduceButton.setOnAction(confirmIntroduceListener);
    }

    public void displayBooks(ObservableList <Book> books){
        booksTable.setItems(books);
        booksTable.refresh();
    }

    public void openUpdateABookWindow() {
        selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Stage updateStage = new Stage();
            VBox updateLayout = new VBox(10);
            updateLayout.setAlignment(Pos.CENTER);
            Scene updateScene = new Scene(updateLayout, 350, 350);
            updateStage.setTitle("Update Book: " + selectedBook.getTitle());

            titleField = new TextField(selectedBook.getTitle());
            authorField = new TextField(selectedBook.getAuthor());
            publishedDateField = new TextField(String.valueOf(selectedBook.getPublishedDate()));
            priceField = new TextField(String.valueOf(selectedBook.getPrice()));
            stockField = new TextField(String.valueOf(selectedBook.getStock()));
            updateLayout.getChildren().addAll(
                    new Label("Title:"), titleField,
                    new Label("Author:"), authorField,
                    new Label("Published Date:"), publishedDateField,
                    new Label("Price:"), priceField,
                    new Label("Stock:"), stockField,
                    confirmUpdateButton
            );
            updateStage.setScene(updateScene);
            updateStage.show();
        } else {
            displayError("You must select a book to update first!");
        }
    }

    public void openIntroduceABookWindow() {
        Stage updateStage = new Stage();
        VBox updateLayout = new VBox(10);
        updateLayout.setAlignment(Pos.CENTER);
        Scene updateScene = new Scene(updateLayout, 300, 350);
        updateStage.setTitle("Introduce a book");

        titleField = new TextField();
        authorField = new TextField();
        priceField = new TextField();
        stockField = new TextField();
        publishedDateField = new TextField();
        updateLayout.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Author:"), authorField,
                new Label("Published Date:"), publishedDateField,
                new Label("Price:"), priceField,
                new Label("Stock:"), stockField,
                confirmIntroduceButton
        );
        updateStage.setScene(updateScene);
        updateStage.show();
    }
    public void displayError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    public void displayMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Book getSelectedBook() {
        selectedBook = booksTable.getSelectionModel().getSelectedItem();
        return selectedBook;
    }

    public TextField getTitleField() {
        return titleField;
    }

    public TextField getAuthorField() {
        return authorField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public TextField getStockField() {
        return stockField;
    }

    public TextField getPublishedDateField() {
        return publishedDateField;
    }

    public Spinner<Integer> getQuantitySpinner() {
        return quantitySpinner;
    }
}