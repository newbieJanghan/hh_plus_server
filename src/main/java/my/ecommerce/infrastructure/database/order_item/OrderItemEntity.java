package my.ecommerce.infrastructure.database.order_item;

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
import my.ecommerce.infrastructure.database.common.BaseEntity;
import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.infrastructure.database.product.ProductEntity;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@Getter
public class OrderItemEntity extends BaseEntity {
	@ManyToOne
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

	public enum OrderItemStatus {
		ORDERED,
		SHIPPED,
		DELIVERED,
		CANCELED,
		RETURNED
	}
}
