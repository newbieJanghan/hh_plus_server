package my.ecommerce.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public Order create(Order order) {
		validate(order);
		return orderRepository.save(order);
	}

	private void validate(Order order) {

	}
}
