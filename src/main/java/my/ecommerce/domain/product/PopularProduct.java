package my.ecommerce.domain.product;

import lombok.Getter;

@Getter
public class PopularProduct extends Product {
	private final int soldAmountInPeriod;

	public PopularProduct(Product product, int soldAmountInPeriod) {
		super(product.getId(), product.getName(), product.getPrice(), product.getStock());
		this.soldAmountInPeriod = soldAmountInPeriod;
	}
}
