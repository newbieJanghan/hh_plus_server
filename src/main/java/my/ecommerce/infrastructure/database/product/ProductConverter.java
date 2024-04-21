package my.ecommerce.infrastructure.database.product;

import org.springframework.stereotype.Component;

import my.ecommerce.domain.product.Product;

@Component
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

	public Product toDomain(JpaProductRepository.PopularProductCustom custom) {
		return Product.builder()
			.id(custom.getProduct().getId())
			.name(custom.getProduct().getName())
			.price(custom.getProduct().getPrice())
			.stock(custom.getProduct().getStock())
			.soldAmountInPeriod((int)custom.getSoldAmountInPeriod())
			.build();
	}
}
