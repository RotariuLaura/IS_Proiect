package repository.book;

import model.AudioBook;
import model.builder.AudioBookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudioBookRepositoryMySql implements AudioBookRepository{
    private final Connection connection;
    public AudioBookRepositoryMySql(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<AudioBook> findAllAudioBooks() {
        String sql = "SELECT * FROM audiobook;";
        List<AudioBook> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                books.add(getAudioBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<AudioBook> findAudioBookById(Long id) {
        String sql = "SELECT * FROM audiobook WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Optional<AudioBook> book = Optional.of(getAudioBookFromResultSet(resultSet));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean saveAudioBook(AudioBook book) {
        String sql = "INSERT INTO audiobook VALUES(null, ?, ?, ?, ?);";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setString(4, String.valueOf(book.getRunTime()));
            int rowsInserted = preparedStatement.executeUpdate();
            return (rowsInserted != 1) ? false : true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAllAudioBooks() {
        String sql = "TRUNCATE TABLE audiobook;";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private AudioBook getAudioBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new AudioBookBuilder()
                .setId(resultSet.getLong("id"))
                .setAuthor(resultSet.getString("author"))
                .setTitle(resultSet.getString("title"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setRunTime(resultSet.getInt("runTime"))
                .build();
    }
}
