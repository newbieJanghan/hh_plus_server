package my.ecommerce.datasource.repository.product;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	public Product findOneById(UUID id) {
		return null;
	}

	public Product save(Product product) {
		return null;
	}
}
