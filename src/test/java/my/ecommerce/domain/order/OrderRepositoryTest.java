package my.ecommerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.Prepare;

@SpringBootTest
public class OrderRepositoryTest {
	@Autowired
	private OrderRepository orderRepository;

	@Test
	@DisplayName("Order 도메인을 저장한 후 영속성 저장된 Order 를 반환 성공")
	public void success_saveAndReturnPersistedOrder() {
		// given
		Order order = Prepare.order(2);

		// when
		Order result = orderRepository.save(order);

		// then
		assertInstanceOf(Order.class, result);
		assertInstanceOf(UUID.class, result.getId());
		assertNotNull(result.getId());
		assertEquals(order.getUserId(), result.getUserId());

		// cleanup
		orderRepository.destroy(result.getId());
	}
}
