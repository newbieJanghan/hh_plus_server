package my.ecommerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.ecommerce.api.controller.abstracts.BaseAuthenticatedController;
import my.ecommerce.api.dto.request.OrderCreateRequest;
import my.ecommerce.api.dto.response.OrderResponse;
import my.ecommerce.domain.order.Order;
import my.ecommerce.usecase.order.OrderUseCase;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController extends BaseAuthenticatedController {
	private final OrderUseCase orderUseCase;

	@Autowired
	public OrdersController(OrderUseCase orderUseCase) {
		this.orderUseCase = orderUseCase;
	}

	@PostMapping("")
	public OrderResponse createOrder(@RequestBody @Valid OrderCreateRequest requestDto) {
		Order order = orderUseCase.run(requestDto.toCommand(getAuthenticatedUser().getId()));
		return OrderResponse.fromDomain(order);
	}
}
