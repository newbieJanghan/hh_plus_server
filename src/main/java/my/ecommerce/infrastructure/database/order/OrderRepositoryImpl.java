package my.ecommerce.infrastructure.database.order;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderRepository;
import my.ecommerce.infrastructure.database.order_item.JpaOrderItemRepository;
import my.ecommerce.infrastructure.database.order_item.OrderItemEntity;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private final JpaOrderRepository jpaOrderRepository;
	private final JpaOrderItemRepository jpaOrderItemRepository;

	@Autowired
	public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository, JpaOrderItemRepository jpaOrderItemRepository) {
		this.jpaOrderRepository = jpaOrderRepository;
		this.jpaOrderItemRepository = jpaOrderItemRepository;
	}

	public Order save(Order domain) {
		OrderEntity order = OrderEntity.fromDomain(domain);
		jpaOrderRepository.save(order);

		List<OrderItemEntity> items = OrderItemEntity.fromDomain(domain.getItems(), order);
		jpaOrderItemRepository.saveAll(items);

		return order.toDomain(items.stream().map(OrderItemEntity::toDomain).toList());
	}

	public void destroy(UUID id) {
		jpaOrderRepository.deleteById(id);
	}
}
