package my.ecommerce.domain.product;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import my.ecommerce.domain.product.popular.PopularProduct;

@Getter
public class Product {
	private final UUID id;
	private final String name;
	@Setter
	private long price;
	@Setter
	private long stock;

	@Builder
	public Product(UUID id, String name, long price, long stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public static Product newProduct(String name) {
		return Product.builder().name(name).build();
	}

	public boolean isAvailable(long quantity) {
		return stock >= quantity;
	}

	public void sell(long quantity) {
		setStock(stock - quantity);
	}

	public PopularProduct toPopularProduct(int soldAmountInPeriod) {
		return new PopularProduct(this, soldAmountInPeriod);
	}
}
