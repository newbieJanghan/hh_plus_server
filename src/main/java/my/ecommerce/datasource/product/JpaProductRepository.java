package my.ecommerce.datasource.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
	@Query(
		value = "SELECT p FROM ProductEntity p WHERE p.createdAt > (SELECT p2.createdAt FROM ProductEntity p2 WHERE p2.id = :cursor) ORDER BY p.createdAt ASC",
		countQuery = "SELECT COUNT(p) FROM ProductEntity p"
	)
	Page<ProductEntity> findAllNextPage(UUID cursor, PageRequest pageRequest);
}
