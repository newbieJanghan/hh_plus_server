package my.ecommerce.domain.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.domain.product.dto.PeriodQuery;

@Repository
public interface ProductRepository {
	Product findById(UUID id);

	Page<Product> findAllWithPage(ProductPageCursorQuery query);

	Page<Product> findAllPopularWithPage(ProductPageCursorQuery query, PeriodQuery period);

	Product save(Product product);

	void destroy(UUID id);
}
