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

import my.ecommerce.domain.Prepare;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemRepository;
import my.ecommerce.domain.product.Product;

public class OrderServiceTest {
	@Captor
	ArgumentCaptor<Order> orderCaptor;
	@Captor
	ArgumentCaptor<OrderItem> orderItemCaptor;

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository orderItemRepository;

	private OrderService orderService;

	@BeforeEach
	void setUp() {
		openMocks(this);
		orderService = new OrderService(orderRepository, orderItemRepository);
	}

	@Test
	@DisplayName("create 메서드는 Order 도메인 객체의 orderItem 과 order 를 영속성 레이어로 전달합니다.")
	void createOrder_thenTransferOrder_toPersistenceLayer_withNullId() {
		// given
		int itemsCount = 2;
		Order order = Prepare.order(itemsCount);
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		when(orderItemRepository.save(any(OrderItem.class))).thenReturn(order.getItems().getFirst());

		// when
		orderService.create(order);

		// then
		verify(orderRepository).save(orderCaptor.capture());
		verify(orderItemRepository, times(itemsCount)).save(orderItemCaptor.capture());

		Order capturedOrder = orderCaptor.getValue();
		assertNull(capturedOrder.getId());
		assertEquals(capturedOrder.getItems().size(), itemsCount);

		OrderItem capturedOrderItem = orderItemCaptor.getValue();
		assertEquals(capturedOrderItem.getOrder(), capturedOrder);
		assertInstanceOf(Product.class, capturedOrderItem.getProduct());
	}
}
