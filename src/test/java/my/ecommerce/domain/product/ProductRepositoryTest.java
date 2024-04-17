package my.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.ecommerce.domain.Prepare;

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
}
