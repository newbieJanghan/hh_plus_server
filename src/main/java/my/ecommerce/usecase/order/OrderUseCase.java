package my.ecommerce.usecase.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.ecommerce.domain.account.AccountService;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.order.dto.OrderCreate;
import my.ecommerce.domain.order.dto.OrderItemCreate;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductService;

@Component
public class OrderUseCase {
	private final OrderService orderService;
	private final ProductService productService;
	private final AccountService accountService;

	@Autowired
	public OrderUseCase(OrderService orderService, ProductService productService, AccountService accountService) {
		this.orderService = orderService;
		this.productService = productService;
		this.accountService = accountService;
	}

	public Order run(OrderCommand command) {
		List<OrderItemCreate> orderItems = command.items().stream().map(item -> {
			Product product = productService.sell(item.productId(), item.quantity());
			return OrderItemCreate.builder()
				.product(product)
				.quantity(item.quantity())
				.currentPrice(product.getPrice())
				.build();
		}).toList();

		OrderCreate orderCreate = OrderCreate.builder()
			.userId(command.userId())
			.items(orderItems)
			.build();

		Order order = orderService.create(orderCreate);
		accountService.use(order.getUserId(), order.getTotalPrice());

		return order;
	}
}
