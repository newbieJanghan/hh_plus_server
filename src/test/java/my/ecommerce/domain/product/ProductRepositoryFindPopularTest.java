package my.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import my.ecommerce.domain.Prepare;
import my.ecommerce.domain.order.OrderCreate;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.product.dto.PeriodQuery;
import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.utils.Today;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class ProductRepositoryFindPopularTest {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderService orderService;

	@Test
	@DisplayName("인기 상품 조회 성공 - PopularProduct 리스트 limit 5까지 반환")
	public void success_findPopularProducts() {
		// given
		int itemsCount = 40;
		setOrderedProducts(itemsCount);
		// when
		ProductPageCursorQuery query = ProductPageCursorQuery.builder()
			.limit(itemsCount)
			.build();
		PeriodQuery period = PeriodQuery.builder()
			.from(Today.beginningOfDay())
			.to(Today.endOfDay())
			.build();

		// then
		Page<Product> result = productRepository.findAllPopularWithPage(query, period);
		List<Product> list = result.getContent();
		Product max = list.stream().max(Comparator.comparingInt(Product::getSoldAmountInPeriod)).get();

		assertEquals(30, list.size());
		assertEquals(list.getFirst(), max);
	}

	private void setOrderedProducts(long itemsCount) {
		List<OrderCreate.OrderItemCreate> items = new ArrayList<>();
		for (int i = 0; i < itemsCount; i++) {
			Product product = productRepository.save(Prepare.product(0, 10));

			OrderCreate.OrderItemCreate orderItem = OrderCreate.OrderItemCreate.builder()
				.product(product)
				.quantity(i + 1)
				.currentPrice(1000L)
				.build();

			items.add(orderItem);
		}

		OrderCreate orderCreate = OrderCreate.builder().userId(UUIDGenerator.generate()).items(items).build();

		orderService.create(orderCreate);
	}
}
