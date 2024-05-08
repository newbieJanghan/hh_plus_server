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
import my.ecommerce.domain.order.order_item.OrderItemCreate;
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
	ArgumentCaptor<List<ProductSell>> productSellListCaptor;
	@Captor
	ArgumentCaptor<OrderCreate> orderCreateCaptor;
	@Captor
	ArgumentCaptor<UUID> userIdCaptor;
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
		Product product = Prepare.product(1000, 10);
		long sellQuantity = 1;

		OrderCommand orderCommand = prepareCommand(product, sellQuantity);
		OrderItemCommand orderItemCommand = orderCommand.items().getFirst();

		Order expect = Prepare.order(orderCommand.userId(), orderCommand.items().size());

		when(productService.sellMany(anyList())).thenReturn(List.of(product));
		when(orderService.create(any(OrderCreate.class))).thenReturn(expect);
		when(accountService.use(any(UUID.class), anyLong())).thenReturn(null);

		// When
		orderUseCase.run(orderCommand);

		verify(productService).sellMany(productSellListCaptor.capture());
		ProductSell productSell = productSellListCaptor.getValue().getFirst();
		assertEquals(product.getId(), productSell.id());
		assertEquals(sellQuantity, productSell.quantity());

		verify(orderService).create(orderCreateCaptor.capture());
		OrderCreate orderCreate = orderCreateCaptor.getValue();
		assertEquals(orderCommand.userId(), orderCreate.userId());
		assertEquals(orderCommand.items().size(), orderCreate.items().size());

		OrderItemCreate orderItemCreate = orderCreate.items().getFirst();
		assertEquals(product.getId(), orderItemCreate.product().getId());
		assertEquals(orderItemCommand.quantity(), orderItemCreate.quantity());

		verify(accountService).use(userIdCaptor.capture(), totalPriceCaptor.capture());
		assertEquals(orderCommand.userId(), userIdCaptor.getValue());
		assertEquals(expect.getTotalPrice(), totalPriceCaptor.getValue());
	}

	private OrderItemCommand prepareOrderItemCommand(Product product, long quantity) {
		return new OrderItemCommand(product.getId(), quantity, 1000L);
	}

	private OrderCommand prepareCommand(Product product, long quantity) {
		return new OrderCommand(UUIDGenerator.generate(),
			List.of(prepareOrderItemCommand(product, quantity)));
	}

}
