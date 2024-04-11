package my.ecommerce.domain.order;

import org.springframework.stereotype.Component;

import my.ecommerce.domain.order.dto.CreateOrderDto;

@Component
public class OrderApp {
	public Order order(CreateOrderDto createOrderDto) {
		return Order.builder().build();
	}
}
