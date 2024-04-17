package my.ecommerce.domain.product.page;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import my.ecommerce.domain.Prepare;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.presentation.page.Sort;

@SpringBootTest
public class ProductPageRepositoryTest {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductPageRepository productPageRepository;

	private List<UUID> testCreatedIdList = new Stack<>();

	// @AfterEach()
	// public void tearDown() {
	// 	for (UUID id : testCreatedIdList) {
	// 		productRepository.destroy(id);
	// 	}
	// }

	@Test
	@DisplayName("상품 목록 조회 성공 - 커서 값이 없음")
	public void success_findAllWithPage_byDefaultQuery() {
		// given
		int itemCount = 20;
		int limit = 10;
		setProductList(itemCount);

		// when
		ProductPageCursorQuery query = ProductPageCursorQuery.builder()
			.limit(limit)
			.sort(Sort.builder().field("createdAt").direction(Sort.Direction.DESC).build())
			.build();
		Page<Product> result = productPageRepository.findAllWithPage(query);

		// then
		assertEquals(limit, result.getSize());
		assertEquals(itemCount, result.getTotalElements());
	}

	/**
	 * TODO 커서 이후의 목록 수가 limit 보다 작은 경우, totalElements()가 전체 목록을 제대로 읽지 못하고 있음.
	 */
	@Test
	@DisplayName("상품 목록 조회 성공 - 커서 값이 있음")
	public void success_findAllWithPage_byCursor() {
		// given
		int itemCount = 30;
		int limit = 10;
		int cursorIndex = 19;
		boolean hasNext = itemCount - (limit + cursorIndex) > 0;
		setProductList(itemCount);
		// List<Product> allItems = productRepository.findAll();
		// UUID cursor = allItems.get(cursorIndex).getId();

		// when
		UUID cursor = testCreatedIdList.get(cursorIndex);
		ProductPageCursorQuery query = ProductPageCursorQuery.builder()
			.limit(limit)
			.cursor(cursor)
			.sort(Sort.builder().field("createdAt").direction(Sort.Direction.DESC).build())
			.build();
		Page<Product> result = productPageRepository.findAllWithPage(query);

		// then
		assertEquals(limit, result.getSize());
		assertEquals(itemCount, result.getTotalElements());
		assertEquals(result.hasNext(), hasNext);
	}

	private void setProductList(int itemCount) {
		for (int i = 0; i < itemCount; i++) {
			Product product = Prepare.product(0, 0);
			productRepository.save(product);

			testCreatedIdList.add(product.getId());
		}
	}
}
