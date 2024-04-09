package my.ecommerce.application.api.orders.dto;

import lombok.Getter;
import my.ecommerce.domain.order.Order;

import java.util.UUID;

@Getter
public class OrderResponseDto {
    private final UUID id;

    public OrderResponseDto(Order order) {
        id = order.getId();
    }
}
