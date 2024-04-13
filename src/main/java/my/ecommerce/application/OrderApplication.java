package my.ecommerce.application;

import org.springframework.stereotype.Component;

import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.dto.CreateOrderDto;

@Component
public class OrderApplication {
	public Order order(CreateOrderDto createOrderDto) {
		return Order.builder().build();
	}
}
