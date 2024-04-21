package my.ecommerce.infrastructure.database.product.custom;

import my.ecommerce.infrastructure.database.product.ProductEntity;

public interface PopularProductCustom {
	ProductEntity getProduct();

	long getSoldAmountInPeriod();
}
