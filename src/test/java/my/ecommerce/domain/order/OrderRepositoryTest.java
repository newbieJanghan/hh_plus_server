package my.ecommerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.Prepare;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class OrderRepositoryTest {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("Order 도메인을 저장한 후 id 값을 부여받은 Order, List<OrderItem> 을 반환합니다.")
	void success_saveAndReturnPersistedOrder() {
		// given
		Product product = productRepository.save(Prepare.product(1000, 10));

		Order order = Order.newOrder(UUIDGenerator.generate());
		order.addOrderItem(product, 1, 1000);
		order.calculateCurrentPrice();

		// when
		Order result = orderRepository.save(order);

		// then
		assertNotNull(result.getId());
		assertEquals(order.getUserId(), result.getUserId());

		assertNotNull(result.getItems().getFirst().getId());

		// cleanup
		orderRepository.destroy(result.getId());
	}
}
