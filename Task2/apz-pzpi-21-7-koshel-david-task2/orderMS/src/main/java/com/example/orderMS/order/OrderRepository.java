package com.example.orderMS.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllBySaloonId(Long id);
    List<Order> findAllByOwnerId(Long id);
}
