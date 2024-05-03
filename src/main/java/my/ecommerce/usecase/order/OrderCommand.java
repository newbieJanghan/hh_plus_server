package my.ecommerce.usecase.order;

import java.util.List;
import java.util.UUID;

import my.ecommerce.domain.order.OrderCreate;
import my.ecommerce.domain.order.order_item.OrderItemCreate;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSell;

public record OrderCommand(UUID userId, List<OrderItemCommand> items) {
	public List<ProductSell> toProductSellList() {
		return items.stream().map(OrderItemCommand::toProductSell).toList();
	}

	public OrderCreate toOrderCreate(List<Product> productList) {
		List<OrderItemCreate> orderItems = items.stream().map(item -> item.toOrderItemCreate(productList)).toList();

		return OrderCreate.builder()
			.userId(userId())
			.items(orderItems)
			.build();
	}
}

