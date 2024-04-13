package my.ecommerce.domain.order.order_item;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import my.ecommerce.domain.product.Product;
import my.ecommerce.utils.UUIDGenerator;

@Getter
public class OrderItem {
	private final UUID id;
	@Delegate
	private final Product product;
	private final long quantity;
	@Setter
	private UUID orderId;
	private OrderItemStatus status;

	@Builder
	public OrderItem(UUID id, UUID orderId, Product product, long quantity, @Nullable OrderItemStatus status) {
		this.id = id;
		this.orderId = orderId;
		this.product = product;
		this.quantity = quantity;
		this.status = status != null ? status : OrderItemStatus.ORDERED;
	}

	public static OrderItem newOrderItem(UUID orderId, Product product, long quantity) {
		return new OrderItem(UUIDGenerator.generate(), orderId, product, quantity, OrderItemStatus.ORDERED);
	}
}
