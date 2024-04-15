package my.ecommerce.datasource.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor
public class OrderEntity {
	@Column
	private UUID userId;

	@Column(name = "total_price", columnDefinition = "LONG DEFAULT 0")
	private long totalPrice;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	@JoinColumn(referencedColumnName = "order_id")
	private List<OrderItemEntity> items;
}
