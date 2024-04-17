package my.ecommerce.domain.order;

import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
	Order save(Order order);

	void destroy(UUID id);
}
