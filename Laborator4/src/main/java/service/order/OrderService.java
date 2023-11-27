package service.order;

import java.sql.Date;

public interface OrderService {
    boolean insertOrder(Long bookId, Long customerId, int quantity, double totalPrice, Date date);
}
