package my.ecommerce.domain.order;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import my.ecommerce.domain.product.Product;

@Builder
public record OrderCreate(UUID userId, List<OrderItemCreate> items) {
	@Builder
	public record OrderItemCreate(Product product, long quantity, long currentPrice) {
	}
}
