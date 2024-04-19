package my.ecommerce.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import my.ecommerce.domain.account.AccountService;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.order.dto.CreateOrderDto;
import my.ecommerce.domain.order.dto.CreateOrderItemDto;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.utils.UUIDGenerator;

public class OrderApplicationTest {
	@Captor
	ArgumentCaptor<UUID> productIdCaptor;
	@Captor
	ArgumentCaptor<Long> quantityCaptor;
	@Captor
	ArgumentCaptor<UUID> userIdCaptor;
	@Captor
	ArgumentCaptor<Long> totalPriceCaptor;
	@Captor
	ArgumentCaptor<Product> productCaptor;
	@Captor
	ArgumentCaptor<Long> sellCountCaptor;
	@Captor
	ArgumentCaptor<Order> orderCaptor;

	@Mock
	OrderService orderService;
	@Mock
	ProductService productService;
	@Mock
	AccountService accountService;

	private OrderApplication orderApplication;

	@BeforeEach()
	void setOrderApplication() {
		openMocks(this);
		this.orderApplication = new OrderApplication(orderService, productService, accountService);
	}

	@Test
	@DisplayName("올바른 Dto 로 각 서비스를 호출하여 주문 성공")
	void success_order() {
		// Given
		Product product = prepareProduct();
		CreateOrderDto orderDto = prepareCreateOrderDto(product);
		CreateOrderItemDto orderItemDto = orderDto.getItems().get(0);

		mockProductServiceGetProduct(orderItemDto, product);
		mockAccountService();
		mockProductServiceSellProduct(product, orderItemDto);
		mockOrderService(orderDto);

		// When
		orderApplication.run(orderDto);

		// Then
		verify(productService).getAvailableProduct(productIdCaptor.capture(), quantityCaptor.capture());
		assertEquals(product.getId(), productIdCaptor.getValue());
		assertEquals(orderItemDto.getQuantity(), quantityCaptor.getValue());

		verify(accountService).use(userIdCaptor.capture(), totalPriceCaptor.capture());
		assertEquals(orderDto.getUserId(), userIdCaptor.getValue());
		assertEquals(orderDto.getTotalPrice(), totalPriceCaptor.getValue());

		verify(productService).sell(productCaptor.capture(), sellCountCaptor.capture());
		assertEquals(product, productCaptor.getValue());
		assertEquals(orderItemDto.getQuantity(), sellCountCaptor.getValue());

		verify(orderService).create(orderCaptor.capture());
		Order order = orderCaptor.getValue();
		assertEquals(orderDto.getUserId(), order.getUserId());
		assertEquals(orderDto.getItems().size(), order.getItems().size());
		assertNull(order.getId());
	}

	private Product prepareProduct() {
		return Product.builder().id(UUIDGenerator.generate()).build();
	}

	private CreateOrderDto prepareCreateOrderDto(Product product) {
		List<CreateOrderItemDto> itemsDto = List.of(CreateOrderItemDto.builder()
			.productId(product.getId())
			.quantity(1)
			.build());

		return CreateOrderDto.builder()
			.userId(UUIDGenerator.generate())
			.items(itemsDto)
			.totalPrice(1000)
			.build();
	}

	private void mockProductServiceGetProduct(CreateOrderItemDto orderItemDto, Product product) {
		when(productService.getAvailableProduct(orderItemDto.getProductId(), orderItemDto.getQuantity())).thenReturn(
			product);
	}

	private void mockProductServiceSellProduct(Product product, CreateOrderItemDto orderItemDto) {
		doNothing().when(productService).sell(product, orderItemDto.getQuantity());
	}

	private void mockAccountService() {
		doNothing().when(accountService).use(any(UUID.class), anyLong());
	}

	private void mockOrderService(CreateOrderDto orderDto) {
		when(orderService.create(any(Order.class))).thenReturn(
			Order.newOrder(orderDto.getUserId(), orderDto.getTotalPrice()));
	}
}
