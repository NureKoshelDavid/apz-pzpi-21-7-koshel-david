package com.example.orderMS.order.impl;

import com.example.orderMS.order.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllBySaloonId(Long id) {
        return orderRepository.findAllBySaloonId(id);
    }

    @Override
    public List<Order> findAllByOwnerId(Long id) {
        return orderRepository.findAllByOwnerId(id);
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteOrderById(Long id) {
        try {
            orderRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateOrderById(Long id, Order updatedOrder) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();

            order.setName(updatedOrder.getName());
            order.setDatetime(updatedOrder.getDatetime());
            order.setTotalCost(updatedOrder.getTotalCost());
            order.setStaffId(updatedOrder.getStaffId());
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    //Загальний прибуток мережі салонів за проміжок часу
    @Override
    public int getProfit(Long ownerId,String time) {
        List<Order> orders;
        try {
            orders = findAllByOwnerId(ownerId);
        }
        catch (Exception E){
            System.out.println("Сталася помилка");
            return 0;
        }
        int profit = 0;
        for (Order order: orders) {
            LocalDateTime orderDateTime = order.getDatetime().toLocalDateTime();
            switch (time) {
                case "1-month":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(1))) {
                        profit += order.getTotalCost();
                    }
                    break;
                case "3-months":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(3))) {
                        profit += order.getTotalCost();
                    }
                    break;
                case "6-months":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(6))) {
                        profit += order.getTotalCost();
                    }
                    break;
                case "12-months":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(12))) {
                        profit += order.getTotalCost();
                    }
                    break;
                case "all-time":
                    profit += order.getTotalCost();
                    break;
                default:
                    profit += order.getTotalCost();
                    break;
            }
        }
        return profit;
    }

    @Override
    public int getSalaryCosts(Long ownerId) {
        int salaryCosts=0;
        RestTemplate restTemplate = new RestTemplate();
        try{
            List<SaloonModel> saloons = restTemplate.exchange("http://localhost:8081/saloons/all/" + ownerId, HttpMethod.GET, null, new ParameterizedTypeReference<List<SaloonModel>>() {}).getBody();

            for (SaloonModel saloon : saloons) {
                List<UserModel> users = restTemplate.exchange("http://localhost:8082/users/staff?saloonId=" + saloon.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<UserModel>>() {}).getBody();

                for (UserModel user: users) {
                    salaryCosts+=user.getSalary();
                }
            }
        }
        catch (Exception e){
            System.out.println("Сталася помилка");
        }

        return salaryCosts;
    }

    //
    @Override
    public int getOrdersAmount(Long ownerId, String time) {
        int ordersAmount = 0;
        List<Order> orders;
        try {
            orders = findAllByOwnerId(ownerId);
        }
        catch (Exception E){
            System.out.println("Сталася помилка");
            return 0;
        }
        for (Order order: orders) {
            LocalDateTime orderDateTime = order.getDatetime().toLocalDateTime();
            switch (time) {
                case "1-month":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(1))) {
                        ordersAmount++;
                    }
                    break;
                case "3-months":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(3))) {
                        ordersAmount++;
                    }
                    break;
                case "6-months":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(6))) {
                        ordersAmount++;
                    }
                    break;
                case "12-months":
                    if (orderDateTime.isAfter(LocalDateTime.now().minusMonths(12))) {
                        ordersAmount++;
                    }
                    break;
                case "all-time":
                    ordersAmount++;
                    break;
                default:
                    ordersAmount++;
                    break;
            }
        }
        return ordersAmount;
    }


}
