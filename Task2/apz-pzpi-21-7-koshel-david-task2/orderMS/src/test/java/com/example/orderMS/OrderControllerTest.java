package com.example.orderMS;

import com.example.orderMS.order.OrderController;
import com.example.orderMS.order.OrderService;
import com.example.orderMS.order.SaloonModel;
import com.example.orderMS.order.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSalaryCosts() {
        // Создание тестовых данных
        SaloonModel saloon1 = new SaloonModel();
        saloon1.setId(1L);
        SaloonModel saloon2 = new SaloonModel();
        saloon2.setId(2L);

        UserModel user1 = new UserModel();
        user1.setId(1L);
        user1.setSalary(1000);
        UserModel user2 = new UserModel();
        user2.setId(2L);
        user2.setSalary(2000);

        // Мокирование ответов от сервера для запросов на салоны
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), any(Class.class)))
                .thenReturn(ResponseEntity.ok(Arrays.asList(saloon1, saloon2)));

        // Мокирование ответов от сервера для запросов на пользователей
        when(restTemplate.exchange(startsWith("http://localhost:8082/users/staff"), eq(HttpMethod.GET), isNull(), any(Class.class)))
                .thenAnswer(invocation -> {
                    String url = invocation.getArgument(0);
                    if (url.contains("saloonId=1")) {
                        return ResponseEntity.ok(Arrays.asList(user1));
                    } else if (url.contains("saloonId=2")) {
                        return ResponseEntity.ok(Arrays.asList(user2));
                    } else {
                        return ResponseEntity.ok(Arrays.asList());
                    }
                });

        // Вызов метода контроллера, который нужно протестировать
        int salaryCosts = orderController.getSalaryCosts(1L);

        // Проверка результата
        assertEquals(1000, salaryCosts);
    }
}
