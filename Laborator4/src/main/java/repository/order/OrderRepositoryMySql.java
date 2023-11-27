package repository.order;

import repository.order.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class OrderRepositoryMySql implements OrderRepository {

    private final Connection connection;

    public OrderRepositoryMySql(Connection connection) {

        this.connection = connection;
    }

    @Override
    public boolean insertOrder(Long bookId, Long customerId, int quantity, double totalPrice, Date date) {
        String sql = "INSERT INTO order_table VALUES(null, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, customerId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, totalPrice);
            preparedStatement.setDate(5, date);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 1) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
