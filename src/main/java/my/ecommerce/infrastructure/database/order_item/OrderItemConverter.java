package my.ecommerce.infrastructure.database.order_item;

import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.infrastructure.database.product.ProductEntity;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemStatus;
import my.ecommerce.domain.product.Product;

public class OrderItemConverter {
	public OrderItemEntity toEntity(OrderItem domain, OrderEntity order, ProductEntity product) {
		return OrderItemEntity.builder()
			.id(domain.getId())
			.order(order)
			.product(product)
			.status(toEntityStatus(domain.getStatus()))
			.quantity(domain.getQuantity())
			.build();
	}

	public OrderItem toDomain(OrderItemEntity entity, Order order, Product product) {
		return OrderItem.builder()
			.id(entity.getId())
			.order(order)
			.product(product)
			.status(toDomainStatus(entity.getStatus()))
			.quantity(entity.getQuantity())
			.currentPrice(entity.getCurrentPrice())
			.build();
	}

	private OrderItemEntity.OrderItemStatus toEntityStatus(OrderItemStatus domain) {
		return OrderItemEntity.OrderItemStatus.valueOf(domain.name());
	}

	private OrderItemStatus toDomainStatus(OrderItemEntity.OrderItemStatus entity) {
		return OrderItemStatus.valueOf(entity.name());
	}
}
