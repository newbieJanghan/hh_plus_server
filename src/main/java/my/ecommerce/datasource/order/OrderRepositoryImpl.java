package my.ecommerce.datasource.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	private JpaOrderRepository jpaRepository;
	private OrderConverter domainConverter = new OrderConverter();

	@Autowired
	public OrderRepositoryImpl(JpaOrderRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public Order save(Order domain) {
		OrderEntity entity = domainConverter.toEntity(domain);
		jpaRepository.save(entity);

		domain.persist(entity.getId());
		return domain;
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
