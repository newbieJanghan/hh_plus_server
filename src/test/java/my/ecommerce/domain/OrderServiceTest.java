package my.ecommerce.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderCreate;
import my.ecommerce.domain.order.OrderRepository;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.order.order_item.OrderItemCreate;
import my.ecommerce.utils.Prepare;
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
	@DisplayName("create 메서드는 OrderCreate 를 받아 Order 를 생성하고 영속성 레이어로 전달합니다.")
	void createOrder_thenTransferOrder_toPersistenceLayer_withNullId() {
		// given
		int itemsCount = 2;
		long pricePerItem = 1000L;
		long stockPerItem = 10;
		long quantityPerItem = 1;

		// given OrderCreate
		List<OrderItemCreate> orderItemCreateList = new ArrayList<>();
		for (int i = 0; i < itemsCount; i++) {
			orderItemCreateList.add(OrderItemCreate.builder()
				.product(Prepare.product(pricePerItem, stockPerItem))
				.quantity(quantityPerItem)
				.currentPrice(pricePerItem)
				.build());
		}
		OrderCreate orderCreate = OrderCreate.builder()
			.userId(UUIDGenerator.generate())
			.items(orderItemCreateList)
			.build();

		when(orderRepository.save(any(Order.class))).thenReturn(Prepare.order(orderCreate.userId(), itemsCount));

		// when
		orderService.create(orderCreate);

		// then
		verify(orderRepository).save(orderCaptor.capture());
		Order captured = orderCaptor.getValue();

		assertEquals(captured.getItems().size(), itemsCount);
		assertEquals(captured.getTotalPrice(), itemsCount * pricePerItem * quantityPerItem);
	}
}
