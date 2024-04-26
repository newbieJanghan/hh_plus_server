package my.ecommerce.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import my.ecommerce.api.dto.page.Sort;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.utils.Prepare;
import my.ecommerce.utils.UUIDGenerator;

public class ProductRepositoryTest extends AbstractRepositoryTest {
	@Autowired
	private ProductRepository productRepository;

	private List<UUID> testCreatedIdList = new Stack<>();

	@Test
	@DisplayName("Product 도메인을 저장한 후 영속성 저장된 Product 를 반환 성공")
	public void success_saveAndReturnPersistedProduct() {
		// given
		Product product = Prepare.product(1000, 10);

		// when
		Product result = productRepository.save(product);

		// then
		assertInstanceOf(Product.class, result);
		assertNotNull(result.getId());
		assertInstanceOf(UUID.class, result.getId());
		assertEquals(product.getName(), result.getName());
		assertEquals(product.getPrice(), result.getPrice());
		assertEquals(product.getStock(), result.getStock());

		// cleanup
		productRepository.destroy(result.getId());
	}

	@Test
	@DisplayName("Product 조회 성공 시 Product 를 반환")
	public void success_findById() {
		// given
		Product product = Prepare.product(1000, 10);
		Product savedProduct = productRepository.save(product);

		// when
		Product result = productRepository.findById(savedProduct.getId());

		// then
		assertInstanceOf(Product.class, result);
		assertEquals(savedProduct.getId(), result.getId());
		assertEquals(savedProduct.getName(), result.getName());
		assertEquals(savedProduct.getPrice(), result.getPrice());
		assertEquals(savedProduct.getStock(), result.getStock());

		// cleanup
		productRepository.destroy(savedProduct.getId());
	}

	@Test
	@DisplayName("Product 조회 실패 시 null 반환")
	public void fail_findById() {
		// when
		Product result = productRepository.findById(UUIDGenerator.generate());

		// then
		assertNull(result);
	}

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
		Page<Product> result = productRepository.findAllWithPage(query);

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
		Page<Product> result = productRepository.findAllWithPage(query);

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
