package com.example.orderMS.order.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.orderMS.order.Order;
import com.example.orderMS.order.OrderRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderServiceImplDiffblueTest {
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    /**
     * Method under test: {@link OrderServiceImpl#findAllBySaloonId(Long)}
     */
    @Test
    void testFindAllBySaloonId() {
        // Arrange
        ArrayList<Order> orderList = new ArrayList<>();
        when(orderRepository.findAllBySaloonId(Mockito.<Long>any())).thenReturn(orderList);

        // Act
        List<Order> actualFindAllBySaloonIdResult = orderServiceImpl.findAllBySaloonId(1L);

        // Assert
        verify(orderRepository).findAllBySaloonId(eq(1L));
        assertTrue(actualFindAllBySaloonIdResult.isEmpty());
        assertSame(orderList, actualFindAllBySaloonIdResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#findAllByOwnerId(Long)}
     */
    @Test
    void testFindAllByOwnerId() {
        // Arrange
        ArrayList<Order> orderList = new ArrayList<>();
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(orderList);

        // Act
        List<Order> actualFindAllByOwnerIdResult = orderServiceImpl.findAllByOwnerId(1L);

        // Assert
        verify(orderRepository).findAllByOwnerId(eq(1L));
        assertTrue(actualFindAllByOwnerIdResult.isEmpty());
        assertSame(orderList, actualFindAllByOwnerIdResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#createOrder(Order)}
     */
    @Test
    void testCreateOrder() {
        // Arrange
        Order order = new Order();
        order.setDatetime(mock(Timestamp.class));
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

        Order order2 = new Order();
        order2.setDatetime(mock(Timestamp.class));
        order2.setId(1L);
        order2.setName("Name");
        order2.setOwnerId(1L);
        order2.setSaloonId(1L);
        order2.setStaffId(1L);
        order2.setTotalCost(1);

        // Act
        orderServiceImpl.createOrder(order2);

        // Assert that nothing has changed
        verify(orderRepository).save(isA(Order.class));
        assertEquals("Name", order2.getName());
        assertEquals(1, order2.getTotalCost());
        assertEquals(1L, order2.getId().longValue());
        assertEquals(1L, order2.getOwnerId().longValue());
        assertEquals(1L, order2.getSaloonId().longValue());
        assertEquals(1L, order2.getStaffId().longValue());
    }

    /**
     * Method under test: {@link OrderServiceImpl#getOrderById(Long)}
     */
    @Test
    void testGetOrderById() {
        // Arrange
        Order order = new Order();
        order.setDatetime(mock(Timestamp.class));
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);
        Optional<Order> ofResult = Optional.of(order);
        when(orderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Order actualOrderById = orderServiceImpl.getOrderById(1L);

        // Assert
        verify(orderRepository).findById(eq(1L));
        assertSame(order, actualOrderById);
    }

    /**
     * Method under test: {@link OrderServiceImpl#deleteOrderById(Long)}
     */
    @Test
    void testDeleteOrderById() {
        // Arrange
        doNothing().when(orderRepository).deleteById(Mockito.<Long>any());

        // Act
        boolean actualDeleteOrderByIdResult = orderServiceImpl.deleteOrderById(1L);

        // Assert
        verify(orderRepository).deleteById(eq(1L));
        assertTrue(actualDeleteOrderByIdResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#updateOrderById(Long, Order)}
     */
    @Test
    void testUpdateOrderById() {
        // Arrange
        Order order = new Order();
        order.setDatetime(mock(Timestamp.class));
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);
        Optional<Order> ofResult = Optional.of(order);

        Order order2 = new Order();
        order2.setDatetime(mock(Timestamp.class));
        order2.setId(1L);
        order2.setName("Name");
        order2.setOwnerId(1L);
        order2.setSaloonId(1L);
        order2.setStaffId(1L);
        order2.setTotalCost(1);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order2);
        when(orderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Order updatedOrder = new Order();
        updatedOrder.setDatetime(mock(Timestamp.class));
        updatedOrder.setId(1L);
        updatedOrder.setName("Name");
        updatedOrder.setOwnerId(1L);
        updatedOrder.setSaloonId(1L);
        updatedOrder.setStaffId(1L);
        updatedOrder.setTotalCost(1);

        // Act
        boolean actualUpdateOrderByIdResult = orderServiceImpl.updateOrderById(1L, updatedOrder);

        // Assert
        verify(orderRepository).findById(eq(1L));
        verify(orderRepository).save(isA(Order.class));
        assertTrue(actualUpdateOrderByIdResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#updateOrderById(Long, Order)}
     */
    @Test
    void testUpdateOrderById2() {
        // Arrange
        Optional<Order> emptyResult = Optional.empty();
        when(orderRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Order updatedOrder = new Order();
        updatedOrder.setDatetime(mock(Timestamp.class));
        updatedOrder.setId(1L);
        updatedOrder.setName("Name");
        updatedOrder.setOwnerId(1L);
        updatedOrder.setSaloonId(1L);
        updatedOrder.setStaffId(1L);
        updatedOrder.setTotalCost(1);

        // Act
        boolean actualUpdateOrderByIdResult = orderServiceImpl.updateOrderById(1L, updatedOrder);

        // Assert
        verify(orderRepository).findById(eq(1L));
        assertFalse(actualUpdateOrderByIdResult);
    }

    /**
     * Method under test: {@link OrderServiceImpl#getProfit(Long, String)}
     */
    @Test
    void testGetProfit() {
        // Arrange
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        int actualProfit = orderServiceImpl.getProfit(1L, "Time");

        // Assert
        verify(orderRepository).findAllByOwnerId(eq(1L));
        assertEquals(0, actualProfit);
    }

    /**
     * Method under test: {@link OrderServiceImpl#getProfit(Long, String)}
     */
    @Test
    void testGetProfit2() {
        // Arrange
        Timestamp datetime = mock(Timestamp.class);
        when(datetime.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        Order order = new Order();
        order.setDatetime(datetime);
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(orderList);

        // Act
        int actualProfit = orderServiceImpl.getProfit(1L, "Time");

        // Assert
        verify(orderRepository).findAllByOwnerId(eq(1L));
        verify(datetime).toLocalDateTime();
        assertEquals(1, actualProfit);
    }

    /**
     * Method under test: {@link OrderServiceImpl#getProfit(Long, String)}
     */
    @Test
    void testGetProfit3() {
        // Arrange
        Timestamp datetime = mock(Timestamp.class);
        when(datetime.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        Timestamp timestamp = mock(Timestamp.class);
        when(timestamp.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        Order order = mock(Order.class);
        when(order.getDatetime()).thenReturn(timestamp);
        doNothing().when(order).setDatetime(Mockito.<Timestamp>any());
        doNothing().when(order).setId(Mockito.<Long>any());
        doNothing().when(order).setName(Mockito.<String>any());
        doNothing().when(order).setOwnerId(Mockito.<Long>any());
        doNothing().when(order).setSaloonId(Mockito.<Long>any());
        doNothing().when(order).setStaffId(Mockito.<Long>any());
        doNothing().when(order).setTotalCost(anyInt());
        order.setDatetime(datetime);
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(orderList);

        // Act
        int actualProfit = orderServiceImpl.getProfit(1L, "1-month");

        // Assert
        verify(order).getDatetime();
        verify(order).setDatetime(isA(Timestamp.class));
        verify(order).setId(eq(1L));
        verify(order).setName(eq("Name"));
        verify(order).setOwnerId(eq(1L));
        verify(order).setSaloonId(eq(1L));
        verify(order).setStaffId(eq(1L));
        verify(order).setTotalCost(eq(1));
        verify(orderRepository).findAllByOwnerId(eq(1L));
        verify(timestamp).toLocalDateTime();
        assertEquals(0, actualProfit);
    }

    /**
     * Method under test: {@link OrderServiceImpl#getOrdersAmount(Long, String)}
     */
    @Test
    void testGetOrdersAmount() {
        // Arrange
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        int actualOrdersAmount = orderServiceImpl.getOrdersAmount(1L, "Time");

        // Assert
        verify(orderRepository).findAllByOwnerId(eq(1L));
        assertEquals(0, actualOrdersAmount);
    }

    /**
     * Method under test: {@link OrderServiceImpl#getOrdersAmount(Long, String)}
     */
    @Test
    void testGetOrdersAmount2() {
        // Arrange
        Timestamp datetime = mock(Timestamp.class);
        when(datetime.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        Order order = new Order();
        order.setDatetime(datetime);
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(orderList);

        // Act
        int actualOrdersAmount = orderServiceImpl.getOrdersAmount(1L, "Time");

        // Assert
        verify(orderRepository).findAllByOwnerId(eq(1L));
        verify(datetime).toLocalDateTime();
        assertEquals(1, actualOrdersAmount);
    }

    /**
     * Method under test: {@link OrderServiceImpl#getOrdersAmount(Long, String)}
     */
    @Test
    void testGetOrdersAmount3() {
        // Arrange
        Timestamp datetime = mock(Timestamp.class);
        when(datetime.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        Timestamp timestamp = mock(Timestamp.class);
        when(timestamp.toLocalDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        Order order = mock(Order.class);
        when(order.getDatetime()).thenReturn(timestamp);
        doNothing().when(order).setDatetime(Mockito.<Timestamp>any());
        doNothing().when(order).setId(Mockito.<Long>any());
        doNothing().when(order).setName(Mockito.<String>any());
        doNothing().when(order).setOwnerId(Mockito.<Long>any());
        doNothing().when(order).setSaloonId(Mockito.<Long>any());
        doNothing().when(order).setStaffId(Mockito.<Long>any());
        doNothing().when(order).setTotalCost(anyInt());
        order.setDatetime(datetime);
        order.setId(1L);
        order.setName("Name");
        order.setOwnerId(1L);
        order.setSaloonId(1L);
        order.setStaffId(1L);
        order.setTotalCost(1);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(orderList);

        // Act
        orderServiceImpl.getOrdersAmount(1L, "1-month");

        // Assert
        verify(order).getDatetime();
        verify(order).setDatetime(isA(Timestamp.class));
        verify(order).setId(eq(1L));
        verify(order).setName(eq("Name"));
        verify(order).setOwnerId(eq(1L));
        verify(order).setSaloonId(eq(1L));
        verify(order).setStaffId(eq(1L));
        verify(order).setTotalCost(eq(1));
        verify(orderRepository).findAllByOwnerId(eq(1L));
        verify(timestamp).toLocalDateTime();
    }
}
