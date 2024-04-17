package my.ecommerce.domain.product;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
	Product findById(UUID id);

	List<Product> findAll();

	Product save(Product product);

	void destroy(UUID id);
}
