package my.ecommerce.datasource.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`order`")
@Getter
@NoArgsConstructor
public class OrderEntity extends BaseEntity {
	@Column
	private UUID userId;

	@Column(name = "total_price", columnDefinition = "BIGINT DEFAULT 0")
	private long totalPrice;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderItemEntity> items = new ArrayList<>();

	public OrderEntity(UUID userId, long totalPrice) {
		this.userId = userId;
		this.totalPrice = totalPrice;
	}
}
