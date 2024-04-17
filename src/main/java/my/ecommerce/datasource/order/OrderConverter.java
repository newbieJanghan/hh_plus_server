package my.ecommerce.datasource.order;

import java.util.List;

import my.ecommerce.datasource.order_item.OrderItemConverter;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;

public class OrderConverter {
	private final OrderItemConverter orderItemConverter = new OrderItemConverter();

	public OrderEntity toEntity(Order domain) {
		return OrderEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.totalPrice(domain.getTotalPrice())
			.build();
	}

	public Order toDomain(OrderEntity entity, List<OrderItem> items) {
		return Order.builder()
			.id(entity.getId())
			.userId(entity.getUserId())
			.totalPrice(entity.getTotalPrice())
			.items(items)
			.build();
	}
}
