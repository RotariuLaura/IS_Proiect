package service.order;

import repository.order.OrderRepository;

import java.sql.Date;

public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    public boolean insertOrder(Long bookId, Long customerId, int quantity, double totalPrice, Date date){
        return orderRepository.insertOrder(bookId, customerId, quantity, totalPrice, date);
    }
}

