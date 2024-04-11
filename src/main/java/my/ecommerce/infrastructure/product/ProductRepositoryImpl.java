package my.ecommerce.infrastructure.product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	public Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramsDto) {
		return null;
	}
}
