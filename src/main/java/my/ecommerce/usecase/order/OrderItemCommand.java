package my.ecommerce.usecase.order;

import java.util.List;
import java.util.UUID;

import my.ecommerce.domain.order.order_item.OrderItemCreate;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSell;

public record OrderItemCommand(UUID productId, long quantity, long currentPrice) {
	public ProductSell toProductSell() {
		return new ProductSell(productId, quantity);
	}

	public OrderItemCreate toOrderItemCreate(List<Product> productList) {
		return OrderItemCreate.builder()
			.product(findProduct(productList))
			.quantity(quantity)
			.currentPrice(currentPrice)
			.build();
	}

	private Product findProduct(List<Product> productList) {
		return productList.stream()
			.filter(p -> {
				if (p.getId() == null)
					throw new IllegalArgumentException("Product id is null");
				return p.getId().equals(productId);
			})
			.findAny()
			.orElse(null);
	}
}
