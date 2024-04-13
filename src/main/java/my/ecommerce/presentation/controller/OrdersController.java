package my.ecommerce.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.ecommerce.application.OrderApplication;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.dto.CreateOrderDto;
import my.ecommerce.presentation.controller.abstracts.BaseAuthenticatedController;
import my.ecommerce.presentation.request.OrderCreateRequest;
import my.ecommerce.presentation.response.OrderResponse;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController extends BaseAuthenticatedController {
	private final OrderApplication orderApplication;

	@Autowired
	public OrdersController(OrderApplication orderApplication) {
		this.orderApplication = orderApplication;
	}

	@PostMapping("")
	public OrderResponse createOrder(@RequestBody @Valid OrderCreateRequest requestDto) {
		CreateOrderDto createOrderDto = requestDto.toDomain(getAuthenticatedUser().getId());
		Order order = orderApplication.order(createOrderDto);
		return OrderResponse.fromDomain(order);
	}
}
