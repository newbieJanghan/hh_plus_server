package my.ecommerce.datasource.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor
public class OrderItemEntity extends BaseEntity {
	@Column(nullable = false, name = "order_id")
	private UUID orderId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

	@Column(nullable = false, columnDefinition = "ENUM('ORDERED', 'SHIPPED', 'DELIVERED', 'CANCELED', 'RETURNED') DEFAULT 'ORDERED'")
	private OrderItemStatus status;
	@Column(nullable = false)
	private long quantity;
	@Column(nullable = false)
	private long paid_price;

	private enum OrderItemStatus {
		ORDERED,
		SHIPPED,
		DELIVERED,
		CANCELED,
		RETURNED
	}
}
