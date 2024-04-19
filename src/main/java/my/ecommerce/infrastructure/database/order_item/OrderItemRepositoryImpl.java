package my.ecommerce.infrastructure.database.order_item;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.infrastructure.database.order.OrderConverter;
import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.infrastructure.database.product.ProductConverter;
import my.ecommerce.infrastructure.database.product.ProductEntity;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemRepository;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
	private JpaOrderItemRepository jpaRepository;
	private OrderItemConverter domainConverter = new OrderItemConverter();
	private OrderConverter orderConverter = new OrderConverter();
	private ProductConverter productConverter = new ProductConverter();

	@Autowired
	public OrderItemRepositoryImpl(JpaOrderItemRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public OrderItem save(OrderItem domain) {
		OrderEntity orderEntity = orderConverter.toEntity(domain.getOrder());
		ProductEntity productEntity = productConverter.toEntity(domain.getProduct());

		OrderItemEntity entity = domainConverter.toEntity(domain, orderEntity, productEntity);
		jpaRepository.save(entity);

		domain.persist(entity.getId());
		return domain;
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
