package my.ecommerce.infrastructure.database.product;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import my.ecommerce.infrastructure.database.product.custom.PopularProductCustom;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
	@Query(
		value = "SELECT p FROM ProductEntity p WHERE p.createdAt > (SELECT p2.createdAt FROM ProductEntity p2 WHERE p2.id = :cursor) ORDER BY p.createdAt ASC",
		countQuery = "SELECT COUNT(p) FROM ProductEntity p"
	)
	Page<ProductEntity> findAllWithPage(UUID cursor, PageRequest pageRequest);

	@Query(
		value =
			"SELECT p as product, SUM(oi.quantity) as soldAmountInPeriod " +
				"FROM ProductEntity p JOIN OrderItemEntity oi ON oi.product.id = p.id " +
				"WHERE p.createdAt > :from AND p.createdAt < :to " +
				"GROUP BY p.id",
		countQuery = "SELECT COUNT(p) FROM ProductEntity p"
	)
	Page<PopularProductCustom> findAllPopularWithPage(LocalDateTime from, LocalDateTime to, PageRequest pageRequest);
}
