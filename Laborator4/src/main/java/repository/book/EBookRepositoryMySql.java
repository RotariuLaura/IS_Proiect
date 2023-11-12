package repository.book;

import model.EBook;
import model.builder.EBookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EBookRepositoryMySql implements EBookRepository{
    private final Connection connection;
    public EBookRepositoryMySql(Connection connection) {

        this.connection = connection;
    }
    @Override
    public List<EBook> findAllEBooks() {
        String sql = "SELECT * FROM ebook;";
        List<EBook> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                books.add(getEBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<EBook> findEBookById(Long id) {
        String sql = "SELECT * FROM ebook WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Optional<EBook> book = Optional.of(getEBookFromResultSet(resultSet));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean saveEBook(EBook book) {
        String sql = "INSERT INTO ebook VALUES(null, ?, ?, ?, ?);";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setString(4, book.getFormat());
            int rowsInserted = preparedStatement.executeUpdate();
            return (rowsInserted != 1) ? false : true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAllEBooks() {
        String sql = "TRUNCATE TABLE ebook;";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    private EBook getEBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new EBookBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setFormat(resultSet.getString("format"))
                .build();
    }
}
