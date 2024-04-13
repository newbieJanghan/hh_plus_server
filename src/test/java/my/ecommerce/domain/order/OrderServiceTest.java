package my.ecommerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import my.ecommerce.utils.UUIDGenerator;

public class OrderServiceTest {

	@Captor
	ArgumentCaptor<Order> orderCaptor;

	@Mock
	private OrderRepository orderRepository;

	private OrderService orderService;

	@BeforeEach
	void setUp() {
		openMocks(this);
		orderService = new OrderService(orderRepository);
	}

	@Test
	@DisplayName("create 실행 시 persistence layer 에 UUID 생성하여 넘김")
	void transferOrder_toPersistenceLayer_withNewUUID() {
		// given
		// Product product = new Product(UUIDGenerator.generate());
		// long totalPrice = 1000;
		// OrderCreateRequest.OrderItemCreateRequest item = new OrderCreateRequest.OrderItemCreateRequest(product.getId(),
		// 	1);
		// OrderCreateRequest requestDto = new OrderCreateRequest(List.of(item), totalPrice);
		//
		// UUID userId = UUID.randomUUID();
		// OrderItem expectedItem = OrderItem.builder().product(product).build();
		// Order expect = Order.builder()
		// 	.id(UUIDGenerator.generate())
		// 	.userId(userId)
		// 	.items(List.of(expectedItem))
		// 	.totalPrice(totalPrice)
		// 	.build();

		// when
		orderService.create(Order.newOrder(UUIDGenerator.generate()));
		// then
		verify(orderRepository).save(orderCaptor.capture());
		Order order = orderCaptor.getValue();
		assertNotNull(order.getId());
		assertInstanceOf(UUID.class, order.getId());
	}

}
