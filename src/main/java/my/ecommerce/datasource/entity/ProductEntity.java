package my.ecommerce.datasource.entity;

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

	@Column(columnDefinition = "LONG DEFAULT 0")
	private long price;

	@Column(columnDefinition = "LONG DEFAULT 0")
	private long stock;
}
