package my.ecommerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.Prepare;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemRepository;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;

@SpringBootTest
public class OrderItemRepositoryTest {
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("OrderItem 도메인을 저장한 후 영속성 저장된 OrderItem 를 반환 성공")
	public void success_saveAndReturnPersistedOrderItem() {
		// given
		Order order = orderRepository.save(Prepare.order(1));
		Product product = productRepository.save(Prepare.product(1000, 10));
		OrderItem orderItem = Prepare.orderItem(order, product);

		// when
		OrderItem persisted = orderItemRepository.save(orderItem);

		// then
		assertInstanceOf(OrderItem.class, persisted);
		assertNotNull(persisted.getId());
		assertInstanceOf(UUID.class, persisted.getId());
		assertEquals(orderItem.getProduct(), persisted.getProduct());
		assertEquals(orderItem.getQuantity(), persisted.getQuantity());

		// cleanup
		orderItemRepository.destroy(persisted.getId());
	}
}
