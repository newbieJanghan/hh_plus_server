package my.ecommerce.infrastructure.database.product.popular;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import my.ecommerce.infrastructure.database.product.ProductEntity;

public interface JpaPopularProductRepository extends JpaRepository<ProductEntity, UUID> {
	@Query(
		value =
			"SELECT p as product, SUM(oi.quantity) as soldAmountInPeriod " +
				"FROM ProductEntity p JOIN OrderItemEntity oi ON oi.product.id = p.id " +
				"WHERE p.createdAt > :from AND p.createdAt < :to " +
				"GROUP BY p.id ",
		countQuery = "SELECT COUNT(p) FROM ProductEntity p"
	)
	Page<PopularProductCustom> findAllWithPage(LocalDateTime from, LocalDateTime to, PageRequest pageRequest);
}
