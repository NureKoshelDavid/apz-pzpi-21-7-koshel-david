package com.example.orderMS.order;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface OrderService {
    List<Order> findAllBySaloonId(Long id);
    List<Order> findAllByOwnerId(Long id);
    void createOrder(Order order);
    Order getOrderById(Long id);
    boolean deleteOrderById(Long id);
    boolean updateOrderById(Long id, Order updatedOrder);
    int getProfit(Long ownerId,String time);

    int getSalaryCosts(Long ownerId);
    int getOrdersAmount(Long ownerId, String time);
}
