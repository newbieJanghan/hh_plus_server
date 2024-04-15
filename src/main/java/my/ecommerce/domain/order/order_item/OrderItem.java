package my.ecommerce.domain.order.order_item;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import my.ecommerce.domain.product.Product;

@Getter
public class OrderItem {
	private final Product product;
	private final long quantity;
	private final long currentPrice;

	@Setter
	private OrderItemStatus status;
	@Setter
	private UUID id;
	@Setter
	private UUID orderId;

	public OrderItem(Product product, long quantity, long currentPrice) {
		this.product = product;
		this.quantity = quantity;
		this.currentPrice = currentPrice;
		this.status = OrderItemStatus.ORDERED;
	}

	public static OrderItem newOrderItem(Product product, long quantity, long currentPrice) {
		return new OrderItem(product, quantity, currentPrice);
	}
}
