package my.ecommerce.domain.order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
	Order save(Order order);
}
