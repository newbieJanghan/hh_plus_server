package my.ecommerce.datasource.order;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.datasource.common.BaseEntity;
import my.ecommerce.datasource.order_item.OrderItemEntity;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor
public class OrderEntity extends BaseEntity {
	@Column(nullable = false, name = "user_id")
	private UUID userId;

	@Column(name = "total_price", columnDefinition = "BIGINT DEFAULT 0")
	private long totalPrice;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
	private List<OrderItemEntity> items;

	@Builder
	public OrderEntity(UUID id, UUID userId, long totalPrice, List<OrderItemEntity> items) {
		super(id);
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}
}
