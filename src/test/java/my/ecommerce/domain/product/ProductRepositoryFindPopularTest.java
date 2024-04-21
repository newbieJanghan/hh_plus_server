package my.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.*;

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
		setOrderedProducts();
		// when
		ProductPageCursorQuery query = ProductPageCursorQuery.builder()
			.limit(10)
			.build();
		PeriodQuery period = PeriodQuery.builder()
			.from(Today.beginningOfDay())
			.to(Today.endOfDay())
			.build();

		// then
		Page<Product> result = productRepository.findAllPopularWithPage(query, period);
		List<Product> list = result.getContent();
		Product max = list.stream().max(Comparator.comparingInt(Product::getSoldAmountInPeriod)).get();

		assertEquals(list.size(), 5);
		assertEquals(list.getFirst(), max);
	}

	private void setOrderedProducts() {
		OrderCreate orderCreate = OrderCreate.builder().userId(UUIDGenerator.generate()).build();
		for (int i = 0; i < 10; i++) {
			Product product = Prepare.product(0, 10);
			productRepository.save(product);

			OrderCreate.OrderItemCreate orderItem = OrderCreate.OrderItemCreate.builder()
				.product(product)
				.quantity(i + 1)
				.currentPrice(1000L)
				.build();

			orderCreate.items().add(orderItem);
		}

		orderService.create(orderCreate);
	}
}
