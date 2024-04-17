package my.ecommerce.domain.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import my.ecommerce.domain.product.exceptions.InsufficientStockException;
import my.ecommerce.utils.UUIDGenerator;

public class ProductServiceTest {
	@Captor
	ArgumentCaptor<Product> productCaptor;
	@Mock
	private ProductRepository productRepository;
	private ProductService productService;

	@BeforeEach
	void setUp() {
		openMocks(this);
		this.productService = new ProductService(productRepository);
	}

	@Test
	@DisplayName("주문 가능한 상품 조회 성공")
	void success_getAvailableProducts() {
		long orderQuantity = 1;
		long stock = 10;

		// Given
		Product product = prepareProduct(stock);
		when(productRepository.findById(product.getId())).thenReturn(product);

		// When & Then
		assertDoesNotThrow(() -> productService.getAvailableProduct(product.getId(), orderQuantity));
	}

	@Test
	@DisplayName("주문 가능한 상품 조회 실패 - 재고 부족")
	void fail_getAvailableProducts() {
		long orderQuantity = 1;
		long stock = 0;

		// Given
		Product product = prepareProduct(stock);
		when(productRepository.findById(product.getId())).thenReturn(product);

		// When & Then
		assertThrows(InsufficientStockException.class,
			() -> productService.getAvailableProduct(product.getId(), orderQuantity));
	}

	@Test
	@DisplayName("상품 판매 성공")
	void success_sellProduct() {
		long orderQuantity = 1;
		long stock = 10;

		// Given
		Product product = prepareProduct(stock);
		when(productRepository.save(product)).thenReturn(product);

		// When
		productService.sell(product, orderQuantity);

		// Then
		verify(productRepository).save(productCaptor.capture());
		assertEquals(stock - orderQuantity, productCaptor.getValue().getStock());
	}

	@Test
	@DisplayName("상품 판매 실패 - 재고 부족")
	void fail_sellProduct() {
		long orderQuantity = 1;
		long stock = 0;

		// Given
		Product product = prepareProduct(stock);

		// When & Then
		assertThrows(InsufficientStockException.class, () -> productService.sell(product, orderQuantity));
	}

	private Product prepareProduct(long stock) {
		return new Product(UUIDGenerator.generate(), "상품", 1000L, stock);
	}
}
