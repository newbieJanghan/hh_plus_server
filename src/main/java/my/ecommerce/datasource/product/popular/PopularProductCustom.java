package my.ecommerce.datasource.product.popular;

import my.ecommerce.datasource.product.ProductEntity;

public interface PopularProductCustom {
	ProductEntity getProduct();

	long getSoldAmountInPeriod();
}
