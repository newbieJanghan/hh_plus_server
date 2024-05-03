package my.ecommerce.usecase;

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
import my.ecommerce.domain.order.OrderCreate;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.domain.product.dto.ProductSell;
import my.ecommerce.usecase.order.OrderCommand;
import my.ecommerce.usecase.order.OrderItemCommand;
import my.ecommerce.usecase.order.OrderUseCase;
import my.ecommerce.utils.Prepare;
import my.ecommerce.utils.UUIDGenerator;

public class OrderUseCaseTest {

	@Captor
	ArgumentCaptor<UUID> userIdCaptor;
	@Captor
	ArgumentCaptor<ProductSell> productSellCaptor;
	@Captor
	ArgumentCaptor<OrderCreate> orderCreateCaptor;
	@Captor
	ArgumentCaptor<Long> totalPriceCaptor;

	@Mock
	OrderService orderService;
	@Mock
	ProductService productService;
	@Mock
	AccountService accountService;

	private OrderUseCase orderUseCase;

	@BeforeEach()
	void setOrderApplication() {
		openMocks(this);
		this.orderUseCase = new OrderUseCase(orderService, productService, accountService);
	}

	@Test
	@DisplayName("올바른 Dto 로 각 서비스를 호출하여 주문 성공")
	void success_order() {
		// Given

		OrderCommand command = prepareCommand();
		Product product = Product.builder().id(command.items().getFirst().productId()).build();
		Order expect = Prepare.order(command.items().size());

		when(orderService.create(any(OrderCreate.class))).thenReturn(expect);
		mockAccountService();
		mockProductServiceSellProduct(command.items().getFirst());

		// When
		orderUseCase.run(command);

		verify(productService).sell(productSellCaptor.capture());
		assertEquals(product.getId(), productSellCaptor.getValue().id());
		assertEquals(command.items().getFirst().quantity(), productSellCaptor.getValue().quantity());

		verify(orderService).create(orderCreateCaptor.capture());
		OrderCreate orderCreate = orderCreateCaptor.getValue();
		assertEquals(command.userId(), orderCreate.userId());
		assertEquals(command.items().size(), orderCreate.items().size());
		assertInstanceOf(Product.class, orderCreate.items().getFirst().product());

		verify(accountService).use(userIdCaptor.capture(), totalPriceCaptor.capture());
		assertEquals(command.userId(), userIdCaptor.getValue());
		assertEquals(expect.getTotalPrice(), totalPriceCaptor.getValue());
	}

	private OrderCommand prepareCommand() {
		return new OrderCommand(UUIDGenerator.generate(),
			List.of(new OrderItemCommand(UUIDGenerator.generate(), 1, 1000L)));
	}

	private void mockProductServiceSellProduct(OrderItemCommand item) {
		doNothing().when(productService).sell(any(ProductSell.class));
	}

	private void mockAccountService() {
		doNothing().when(accountService).use(any(UUID.class), anyLong());
	}

}
