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

	public Order create(OrderCreate orderCreate) {
		Order order = Order.newOrder(orderCreate.userId());

		orderCreate.items().forEach(item -> order.addOrderItem(item.product(), item.quantity(), item.currentPrice()));
		order.calculateCurrentPrice();

		validate(order);

		return orderRepository.save(order);
	}

	private void validate(Order order) {

	}
}
