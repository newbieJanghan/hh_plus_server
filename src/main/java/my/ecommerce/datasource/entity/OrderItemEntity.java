package my.ecommerce.datasource.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Column(nullable = false)
	private long paid_price;

	public OrderItemEntity(UUID id, OrderEntity order, ProductEntity product, OrderItemStatus status, long quantity,
		long paid_price) {
		super(id);
		this.order = order;
		this.product = product;
		this.status = status;
		this.quantity = quantity;
		this.paid_price = paid_price;
	}

	private enum OrderItemStatus {
		ORDERED,
		SHIPPED,
		DELIVERED,
		CANCELED,
		RETURNED
	}
}
