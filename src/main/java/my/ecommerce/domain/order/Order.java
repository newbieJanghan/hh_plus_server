package my.ecommerce.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.product.Product;

@Getter
public class Order extends BaseDomain {
	private final UUID userId;
	private final List<OrderItem> items;
	private long totalPrice;

	@Builder
	public Order(UUID id, UUID userId, List<OrderItem> items, long totalPrice) {
		this.id = id;
		this.userId = userId;
		this.items = items;
		this.totalPrice = totalPrice;
	}

	public static Order newOrder(UUID userId) {
		return new Order(null, userId, new ArrayList<>(), 0);
	}

	public void addOrderItem(Product product, long quantity, long currentPrice) {
		OrderItem item = OrderItem.newOrderItem(this, product, quantity, currentPrice);
		items.add(item);
	}

	public void calculateCurrentPrice() {
		items.forEach(item -> totalPrice += item.getCurrentPrice() * item.getQuantity());
	}
}
