package my.ecommerce.datasource.order;

import org.springframework.stereotype.Repository;

import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	public Order save(Order order) {
		return null;
	}
}
