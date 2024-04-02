package my.ecommerce.application.orders.dto;

import my.ecommerce.application.common.Response;
import my.ecommerce.domain.order.Order;

public class OrderResponseDto extends Response<Order> {
    public OrderResponseDto(Order data) {
        super("OK", data);
    }
}
