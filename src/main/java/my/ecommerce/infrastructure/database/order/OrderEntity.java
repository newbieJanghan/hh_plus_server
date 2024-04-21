package my.ecommerce.infrastructure.database.order;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.infrastructure.database.common.BaseEntity;
import my.ecommerce.infrastructure.database.order_item.OrderItemEntity;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor
public class OrderEntity extends BaseEntity {
	@Column(nullable = false, name = "user_id")
	private UUID userId;

	@Column(name = "total_price", columnDefinition = "BIGINT DEFAULT 0")
	private long totalPrice;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<OrderItemEntity> items;

	@Builder
	public OrderEntity(UUID id, UUID userId, long totalPrice, List<OrderItemEntity> items) {
		super(id);
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}

	public static OrderEntity fromDomain(Order domain) {
		return OrderEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.totalPrice(domain.getTotalPrice())
			.build();
	}

	public Order toDomain() {
		return Order.builder()
			.id(getId())
			.userId(userId)
			.totalPrice(totalPrice)
			.build();
	}

	public Order toDomain(List<OrderItem> items) {
		return Order.builder()
			.id(getId())
			.userId(userId)
			.items(items)
			.totalPrice(totalPrice)
			.build();
	}
}
