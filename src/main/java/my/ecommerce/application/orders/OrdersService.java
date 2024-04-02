package my.ecommerce.application.orders;

import my.ecommerce.application.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.orders.dto.OrderResponseDto;
import my.ecommerce.domain.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    public OrderResponseDto create(long userId, CreateOrderRequestDto requestDto) {
        return new OrderResponseDto(new Order());
    }
}
