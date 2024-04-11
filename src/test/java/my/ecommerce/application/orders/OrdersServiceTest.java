package my.ecommerce.application.orders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import my.ecommerce.application.api.orders.OrdersService;
import my.ecommerce.application.api.orders.dto.CreateOrderRequestDto;
import my.ecommerce.application.api.orders.dto.CreateOrderRequestOrderItemDto;
import my.ecommerce.application.api.orders.dto.OrderResponseDto;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderApp;
import my.ecommerce.domain.order.dto.CreateOrderDto;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.product.Product;
import my.ecommerce.utils.UUIDGenerator;

public class OrdersServiceTest {

	@Mock
	private OrderApp orderApp;

	private OrdersService ordersService;

	@BeforeEach
	void setUp() {
		openMocks(this);
		ordersService = new OrdersService(orderApp);
	}

	@Test
	@DisplayName("create order 성공")
	void success_createOrder() {
		// given
		Product product = Product.builder().id(UUID.randomUUID()).build();
		long totalPrice = 1000;
		CreateOrderRequestOrderItemDto item = new CreateOrderRequestOrderItemDto(product.getId(), 1);
		CreateOrderRequestDto requestDto = new CreateOrderRequestDto(List.of(item), totalPrice);

		UUID userId = UUID.randomUUID();
		OrderItem expectedItem = OrderItem.builder().product(product).build();
		Order expect = Order.builder()
			.id(UUIDGenerator.generate())
			.userId(userId)
			.items(List.of(expectedItem))
			.totalPrice(totalPrice)
			.build();
		when(orderApp.order(any(CreateOrderDto.class))).thenReturn(expect);

		// when
		OrderResponseDto result = ordersService.create(userId, requestDto);
		// then
		assertEquals(result.getUserId(), userId);
		assertNotNull(result.getId());
		assertEquals(1, result.getItems().size());
		assertEquals(product.getId(), result.getItems().getFirst().getProduct().getId());
		assertEquals(totalPrice, result.getTotalPrice());
	}

}
