package my.ecommerce.domain.order;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.order.order_item.OrderItem;

@Getter
@NoArgsConstructor
public class Order {
	private UUID id;
	private UUID userId;
	private long totalPrice;
	private List<OrderItem> items;

	@Builder
	private Order(UUID id, UUID userId, long totalPrice, List<OrderItem> items) {
		this.id = id;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}
}
