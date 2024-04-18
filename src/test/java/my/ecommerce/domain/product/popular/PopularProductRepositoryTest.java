package my.ecommerce.domain.product.popular;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import my.ecommerce.domain.Prepare;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.OrderService;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.utils.Today;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class PopularProductRepositoryTest {
	@Autowired
	private PopularProductRepository popularProductRepository;

	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductRepository productRepository;

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
		Page<PopularProduct> result = popularProductRepository.findPopularWithPage(query, period);
		List<PopularProduct> list = result.getContent();
		PopularProduct max = list.stream().max(Comparator.comparingInt(PopularProduct::getSoldAmountInPeriod)).get();

		assertEquals(list.size(), 5);
		assertInstanceOf(PopularProduct.class, list.getFirst());
		assertEquals(list.getFirst(), max);
	}

	private void setOrderedProducts() {
		Order order = Order.newOrder(UUIDGenerator.generate(), 10000);
		for (int i = 0; i < 10; i++) {
			Product product = Prepare.product(0, 10);
			productRepository.save(product);

			order.addOrderItem(product, i + 1, 1000L);
		}

		orderService.create(order);
	}
}
