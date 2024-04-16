package my.ecommerce.datasource.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.page.CursorPagedProductsQueryDto;
import my.ecommerce.domain.product.page.ProductPageRepository;
import my.ecommerce.domain.product.popular.PopularProduct;
import my.ecommerce.domain.product.popular.PopularProductsPeriodQueryDto;

@Repository
public class ProductPageRepositoryImpl implements ProductPageRepository {
	public Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramsDto) {
		return null;
	}

	public Page<PopularProduct> findAllPopularWithPage(CursorPagedProductsQueryDto paramsDto,
		PopularProductsPeriodQueryDto periodDto) {
		return null;
	}

}
