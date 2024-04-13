package my.ecommerce.domain.order;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.product.Product;

@Getter

public class Order {
	private final UUID id;
	private final UUID userId;
	@Setter
	private long totalPrice;
	private List<OrderItem> items;

	@Builder
	public Order(UUID id, UUID userId, long totalPrice, List<OrderItem> items) {
		this.id = id;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}

	public static Order newOrder(UUID userId) {
		return new Order(UUID.randomUUID(), userId, 0, List.of());
	}

	public void addOrderItem(Product product, long quantity) {
		OrderItem item = OrderItem.newOrderItem(this.getId(), product, quantity);
		items.add(item);
		totalPrice += item.getProduct().getPrice() * item.getQuantity();
	}
}
