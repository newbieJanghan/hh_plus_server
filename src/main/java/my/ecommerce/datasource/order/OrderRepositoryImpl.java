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
		System.out.println("domain");
		System.out.println("id: " + domain.getId());
		System.out.println("userId: " + domain.getUserId());
		System.out.println("items: " + domain.getItems());
		OrderEntity entity = domainConverter.toEntity(domain);
		System.out.println("entity before save");
		System.out.println("id: " + entity.getId());
		System.out.println("userId: " + entity.getUserId());
		System.out.println("items: " + entity.getItems());
		jpaRepository.save(entity);
		System.out.println("after save");
		System.out.println("id: " + entity.getId());
		System.out.println("userId: " + entity.getUserId());
		System.out.println("createdAt: " + entity.getCreatedAt());
		System.out.println("updatedAt: " + entity.getUpdatedAt());

		domain.persist(entity.getId());
		return domain;
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
