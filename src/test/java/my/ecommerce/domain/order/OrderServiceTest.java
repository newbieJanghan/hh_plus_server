package my.ecommerce.domain.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

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
	@DisplayName("create 메서드는 validation 실행 후 persistence layer 에 order 를 전달합니다.")
	void createOrder_thenTransferOrder_toPersistenceLayer_withNullId() {
		// given
		Order order = Order.newOrder(null, 0);
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		// when
		orderService.create(order);
		// then
		verify(orderRepository).save(orderCaptor.capture());
		Order captured = orderCaptor.getValue();
		assertNull(captured.getId());
	}

}
