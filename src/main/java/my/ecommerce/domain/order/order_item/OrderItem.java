package my.ecommerce.domain.order.order_item;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.product.Product;

@Getter
public class OrderItem extends BaseDomain {
	private final Product product;
	private final long quantity;
	private final long currentPrice;

	@Setter
	private Order order;
	@Setter
	private OrderItemStatus status;

	@Builder
	public OrderItem(UUID id, Order order, Product product, long quantity, long currentPrice, OrderItemStatus status) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.currentPrice = currentPrice;
		this.status = status;
	}

	public static OrderItem newOrderItem(Order order, Product product, long quantity, long currentPrice) {
		return new OrderItem(null, order, product, quantity, currentPrice, OrderItemStatus.ORDERED);
	}
}
