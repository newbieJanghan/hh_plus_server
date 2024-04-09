package my.ecommerce.application.api.orders;

import my.ecommerce.application.api.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.api.orders.dto.OrderResponseDto;
import my.ecommerce.domain.order.Order;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrdersService {
    public OrderResponseDto create(UUID userId, CreateOrderRequestDto requestDto) {
        return new OrderResponseDto(new Order());
    }
}
