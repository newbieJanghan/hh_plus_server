package my.ecommerce.domain.product.page;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.popular.PopularProduct;
import my.ecommerce.domain.product.popular.PopularProductsPeriodQueryDto;

@Repository
public interface ProductPageRepository {
	Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramsDto);

	Page<PopularProduct> findAllPopularWithPage(CursorPagedProductsQueryDto paramDto,
		PopularProductsPeriodQueryDto periodDto);
}
