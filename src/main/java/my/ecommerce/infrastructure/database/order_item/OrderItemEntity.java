package my.ecommerce.infrastructure.database.order_item;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.infrastructure.database.common.BaseEntity;
import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.infrastructure.database.product.ProductEntity;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@Getter
public class OrderItemEntity extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderItemStatus status;

	@Column(nullable = false)
	private long quantity;

	@Column(name = "current_price", nullable = false)
	private long currentPrice;

	@Builder
	public OrderItemEntity(UUID id, OrderEntity order, ProductEntity product, long quantity,
		long currentPrice, OrderItemStatus status) {
		super(id);
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.currentPrice = currentPrice;
		this.status = status;
	}

	public static OrderItemEntity fromDomain(OrderItem domain, OrderEntity order) {
		return OrderItemEntity.builder()
			.id(domain.getId())
			.order(order)
			.product(ProductEntity.fromDomain(domain.getProduct()))
			.status(OrderItemStatus.valueOf(domain.getStatus().name()))
			.quantity(domain.getQuantity())
			.currentPrice(domain.getCurrentPrice())
			.build();
	}

	public static List<OrderItemEntity> fromDomain(List<OrderItem> list, OrderEntity order) {
		return list.stream().map(item -> fromDomain(item, order)).toList();
	}

	public OrderItem toDomain() {
		return OrderItem.builder()
			.id(getId())
			.status(OrderItem.OrderItemStatus.valueOf(status.name()))
			.quantity(quantity)
			.currentPrice(currentPrice)
			.build();
	}

	public enum OrderItemStatus {
		ORDERED,
		SHIPPED,
		DELIVERED,
		CANCELED,
		RETURNED
	}
}
