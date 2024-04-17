package my.ecommerce.domain.product.page;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.popular.PeriodQuery;
import my.ecommerce.domain.product.popular.PopularProduct;

@Repository
public interface ProductPageRepository {
	Page<Product> findAllWithPage(ProductPageCursorQuery paramsDto);

	Page<PopularProduct> findAllPopularWithPage(ProductPageCursorQuery paramDto,
		PeriodQuery periodDto);
}
