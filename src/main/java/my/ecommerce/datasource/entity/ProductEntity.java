package my.ecommerce.datasource.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class ProductEntity extends BaseEntity {
	@Column(nullable = false)
	private String name;

	@Column(columnDefinition = "BIGINT DEFAULT 0")
	private long price;

	@Column(columnDefinition = "BIGINT DEFAULT 0")
	private long stock;

	public ProductEntity(UUID id, String name, long price, long stock) {
		super(id);
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
}
