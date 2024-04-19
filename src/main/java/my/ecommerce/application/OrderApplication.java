package my.ecommerce.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.ecommerce.domain.account.AccountService;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.order.dto.CreateOrderDto;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductService;

@Component
public class OrderApplication {
	private final OrderService orderService;
	private final ProductService productService;
	private final AccountService accountService;

	@Autowired
	public OrderApplication(OrderService orderService, ProductService productService, AccountService accountService) {
		this.orderService = orderService;
		this.productService = productService;
		this.accountService = accountService;
	}

	public Order run(CreateOrderDto createOrderDto) {
		Order order = Order.newOrder(createOrderDto.getUserId(), createOrderDto.getTotalPrice());
		createOrderDto.getItems().forEach(item -> {
			Product product = productService.getAvailableProduct(item.getProductId(), item.getQuantity());
			order.addOrderItem(product, item.getQuantity(), item.getCurrentPrice());
		});

		accountService.use(order.getUserId(), order.getTotalPrice());

		order.getItems().forEach(item -> productService.sell(item.getProduct(), item.getQuantity()));

		return orderService.create(order);
	}
}
