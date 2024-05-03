package my.ecommerce.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.domain.product.dto.ProductSell;
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
	@DisplayName("상품 판매 성공")
	void success_sellProduct() {
		long orderQuantity = 1;
		long stock = 10;

		// Given
		Product product = prepareProduct(stock);
		ProductSell productSell = new ProductSell(product.getId(), orderQuantity);

		when(productRepository.findById(product.getId())).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);

		// When
		productService.sell(productSell);

		// Then
		verify(productRepository).save(productCaptor.capture());
		assertEquals(stock - orderQuantity, productCaptor.getValue().getStock());
	}

	@Test
	@DisplayName("상품 판매 실패 - 재고 부족")
	void fail_sellProduct() {
		long orderQuantity = 1;
		long stock = 0;

		Product product = prepareProduct(stock);
		ProductSell productSell = new ProductSell(product.getId(), orderQuantity);

		// Given
		when(productRepository.findById(product.getId())).thenReturn(product);

		// When & Then
		assertThrows(InsufficientStockException.class,
			() -> productService.sell(productSell));
	}

	private Product prepareProduct(long stock) {
		return Product.builder()
			.id(UUIDGenerator.generate())
			.name("상품").price(1000L).stock(stock)
			.build();
	}
}
