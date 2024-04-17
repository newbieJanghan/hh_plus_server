package my.ecommerce.domain.product;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;
import my.ecommerce.domain.product.popular.PopularProduct;

@Getter
public class Product extends BaseDomain {
	private final String name;
	private long price;
	private long stock;

	@Builder
	public Product(UUID id, String name, long price, long stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public static Product newProduct(String name, long price, long stock) {
		return Product.builder()
			.name(name)
			.price(price)
			.stock(stock)
			.build();
	}

	public boolean isAvailable(long quantity) {
		return stock >= quantity;
	}

	public void sell(long quantity) {
		stock -= quantity;
	}

	public PopularProduct toPopularProduct(int soldAmountInPeriod) {
		return new PopularProduct(this, soldAmountInPeriod);
	}
}
