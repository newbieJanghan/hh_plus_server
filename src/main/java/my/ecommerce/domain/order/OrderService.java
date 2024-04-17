package my.ecommerce.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
	}

	public Order create(Order order) {
		validate(order);
		Order persistedOrder = orderRepository.save(order);
		order.persist(persistedOrder.getId());

		order.getItems().forEach(item -> {
			item.setOrder(persistedOrder);
			OrderItem persisted = orderItemRepository.save(item);
			item.persist(persisted.getId());
		});

		return order;
	}

	private void validate(Order order) {

	}
}
