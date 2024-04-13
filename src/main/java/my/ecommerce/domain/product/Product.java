package my.ecommerce.domain.product;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {
	private final UUID id;
	private final String name;
	private final long price;
	private final long stock;

	@Builder
	public Product(UUID id, String name, long price, long stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public PopularProduct toPopularProduct(int soldAmountInPeriod) {
		return new PopularProduct(this, soldAmountInPeriod);
	}
}
