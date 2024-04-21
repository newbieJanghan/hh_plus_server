package my.ecommerce.infrastructure.database.product.custom;

import my.ecommerce.domain.product.Product;

public class PopularProductConverter {
	public Product toDomain(PopularProductCustom custom) {
		return Product.builder()
			.id(custom.getProduct().getId())
			.name(custom.getProduct().getName())
			.price(custom.getProduct().getPrice())
			.stock(custom.getProduct().getStock())
			.soldAmountInPeriod((int)custom.getSoldAmountInPeriod())
			.build();
	}
}
