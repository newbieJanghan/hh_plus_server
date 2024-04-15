package my.ecommerce.domain.product;

import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
	Product findOneById(UUID id);

	Product save(Product product);
}
