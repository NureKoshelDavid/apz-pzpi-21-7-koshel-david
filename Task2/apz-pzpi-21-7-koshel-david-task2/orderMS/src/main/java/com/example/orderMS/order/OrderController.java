package com.example.orderMS.order;


import com.fasterxml.jackson.core.ObjectCodec;
import org.aspectj.weaver.ast.Or;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<Order> findAllBySaloonId(@RequestParam Long saloonId){
        return orderService.findAllBySaloonId(saloonId);
    }

    @GetMapping("/all/{ownerId}")
    public List<Order> findAllByOwnerId(@PathVariable Long ownerId){
        return orderService.findAllByOwnerId(ownerId);
    }

    @PostMapping()
    public void createOrder(@RequestBody Order order){
        orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable Long id){
        return orderService.deleteOrderById(id);
    }

    @PutMapping("/{id}")
    public boolean updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder){
        return orderService.updateOrderById(id,updatedOrder);
    }

    @GetMapping("/statistics/salary-costs/{ownerId}")
    // Вирахування суми заробітних плат працівників
    public int getSalaryCosts(@PathVariable Long ownerId){
        return orderService.getSalaryCosts(ownerId);
    }

    @GetMapping("/statistics/profit/{ownerId}")
    public int getProfit(@PathVariable Long ownerId, @RequestParam String time){
        return orderService.getProfit(ownerId,time);
    }

    @GetMapping("/statistics/net-profit/{ownerId}")
    public int getNetProfit(@PathVariable Long ownerId){
        return getProfit(ownerId,"1-month")-getSalaryCosts(ownerId);
    }

    @GetMapping("/statistics/orders-amount/{ownerId}")
    public int getOrdersAmount(@PathVariable Long ownerId, @RequestParam String time){
        return orderService.getOrdersAmount(ownerId,time);
    }

}
