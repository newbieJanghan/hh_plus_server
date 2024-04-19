package my.ecommerce.domain.product;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.BaseDomain;

@Getter
public class Product extends BaseDomain {
	private final String name;
	private final long price;
	private final int soldAmountInPeriod;
	private long stock;

	@Builder
	public Product(UUID id, String name, long price, long stock, int soldAmountInPeriod) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.soldAmountInPeriod = soldAmountInPeriod;
	}

	public boolean isAvailable(long quantity) {
		return stock >= quantity;
	}

	public void sell(long quantity) {
		stock -= quantity;
	}
}
