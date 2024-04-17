package my.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.Prepare;
import my.ecommerce.utils.UUIDGenerator;

@SpringBootTest
public class ProductRepositoryTest {
	@Autowired
	private ProductRepository productRepository;

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
}
