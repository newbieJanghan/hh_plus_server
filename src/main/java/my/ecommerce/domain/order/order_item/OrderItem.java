package my.ecommerce.domain.order.order_item;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.product.Product;

@Getter
@NoArgsConstructor
public class OrderItem {
	private UUID id;
	private UUID orderId;
	private Product product;
	private long quantity;
	private OrderItemStatus status;

	@Builder
	private OrderItem(UUID id, UUID orderId, Product product, long quantity, @Nullable OrderItemStatus status) {
		this.id = id;
		this.orderId = orderId;
		this.product = product;
		this.quantity = quantity;
		this.status = status != null ? status : OrderItemStatus.ORDERED;
	}
}
