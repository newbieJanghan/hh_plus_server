package my.ecommerce.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.product.Product;

@Getter
public class Order extends BaseDomain {
	private final UUID userId;
	private final List<OrderItem> items;
	@Setter
	private long totalPrice;

	public Order(UUID id, UUID userId, long totalPrice, List<OrderItem> items) {
		this.id = id;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}

	public static Order newOrder(UUID userId, long totalPrice) {
		return new Order(null, userId, totalPrice, new ArrayList<>());
	}

	public void addOrderItem(Product product, long quantity, long currentPrice) {
		OrderItem item = OrderItem.newOrderItem(product, quantity, currentPrice);
		items.add(item);
	}

	public long calculateCurrentPrice() {
		return items.stream().map(OrderItem::getCurrentPrice).reduce(Long::sum).orElse(0L);
	}
}
