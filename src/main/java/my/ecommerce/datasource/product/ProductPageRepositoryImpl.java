package my.ecommerce.datasource.product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.PopularProduct;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductPageRepository;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.domain.product.dto.PopularProductsPeriodQueryDto;

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
