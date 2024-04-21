package my.ecommerce.usecase.order;

import java.util.List;
import java.util.UUID;

import my.ecommerce.domain.order.OrderCreate;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSell;

public record OrderCommand(UUID userId, List<OrderItemCommand> items) {
	public List<ProductSell> toProductSell() {
		return items.stream().map(item -> new ProductSell(item.productId(), item.quantity())).toList();
	}

	public OrderCreate toOrderCreate(List<Product> products) {
		List<OrderCreate.OrderItemCreate> orderItems = items.stream().map(item -> {
			Product product = products.stream()
				.filter(p -> p.getId().equals(item.productId()))
				.findFirst()
				.orElseThrow();
			return OrderCreate.OrderItemCreate.builder()
				.product(product)
				.quantity(item.quantity())
				.currentPrice(product.getPrice())
				.build();
		}).toList();

		return OrderCreate.builder()
			.userId(userId())
			.items(orderItems)
			.build();
	}

	public record OrderItemCommand(UUID productId, long quantity, long currentPrice) {
	}
}

