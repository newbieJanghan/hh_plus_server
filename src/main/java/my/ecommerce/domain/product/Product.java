package my.ecommerce.domain.product;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Product {
	private final UUID id;
	private String name;
	private long price;
	private long stock;

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
