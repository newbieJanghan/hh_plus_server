package my.ecommerce.datasource.product;

import my.ecommerce.domain.product.Product;

public class ProductConverter {
	public ProductEntity toEntity(Product domain) {
		return ProductEntity.builder()
			.id(domain.getId())
			.name(domain.getName())
			.price(domain.getPrice())
			.stock(domain.getStock())
			.build();
	}

	public Product toDomain(ProductEntity entity) {
		return Product.builder()
			.id(entity.getId())
			.name(entity.getName())
			.price(entity.getPrice())
			.stock(entity.getStock())
			.build();
	}
}
