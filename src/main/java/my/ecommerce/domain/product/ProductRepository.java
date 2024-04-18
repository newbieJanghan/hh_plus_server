package my.ecommerce.domain.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.page.ProductPageCursorQuery;

@Repository
public interface ProductRepository {
	Product findById(UUID id);

	Page<Product> findAllWithPage(ProductPageCursorQuery paramsDto);

	Product save(Product product);

	void destroy(UUID id);
}
