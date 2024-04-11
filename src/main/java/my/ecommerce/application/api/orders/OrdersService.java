package my.ecommerce.application.api.orders;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ecommerce.application.api.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.api.orders.dto.OrderResponseDto;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderApp;

@Service
public class OrdersService {
	private final OrderApp orderApp;

	@Autowired
	public OrdersService(OrderApp orderApp) {
		this.orderApp = orderApp;
	}

	public OrderResponseDto create(UUID userId, CreateOrderRequestDto requestDto) {
		Order order = orderApp.order(requestDto.toDomain(userId));

		return OrderResponseDto.fromDomain(order);
	}
}
