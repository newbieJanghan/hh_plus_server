package my.ecommerce.domain.product.popular;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.page.ProductPageCursorQuery;

@Repository
public interface PopularProductRepository {
	Page<PopularProduct> findPopularWithPage(ProductPageCursorQuery query, PeriodQuery period);
}
