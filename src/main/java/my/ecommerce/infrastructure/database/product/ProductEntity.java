package my.ecommerce.infrastructure.database.product;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.product.Product;
import my.ecommerce.infrastructure.database.common.BaseEntity;

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

	@Builder
	public ProductEntity(UUID id, String name, long price, long stock) {
		super(id);
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public static ProductEntity fromDomain(Product domain) {
		return ProductEntity.builder()
			.id(domain.getId())
			.name(domain.getName())
			.price(domain.getPrice())
			.stock(domain.getStock())
			.build();
	}

	public Product toDomain() {
		return Product.builder()
			.id(getId())
			.name(name)
			.price(price)
			.stock(stock)
			.build();
	}
}
