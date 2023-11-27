package repository.order;

import java.sql.Date;

public interface OrderRepository {
    boolean insertOrder(Long bookId, Long customerId, int quantity, double totalPrice, Date date);
}
