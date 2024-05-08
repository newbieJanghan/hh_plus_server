package my.ecommerce.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.domain.product.dto.ProductSell;
import my.ecommerce.domain.product.exceptions.InsufficientStockException;
import my.ecommerce.utils.AbstractTestWithDatabase;
import my.ecommerce.utils.Prepare;

public class IProductServiceTest extends AbstractTestWithDatabase {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("상품 재고 감소 성공")
	void success_decreaseStock() {
		// given
		long currentStock = 100;
		long decreaseStock = 10;
		Product product = Prepare.product(1000, currentStock);
		productRepository.save(product);

		// when
		Product sold = productService.sell(new ProductSell(product.getId(), decreaseStock));

		// then
		assertEquals(currentStock - decreaseStock, sold.getStock());
	}

	@Test
	@DisplayName("동시성 제어 성공 - 동시의 판매 요청에 절반은 재고 부족 에러 발생")
	void success_concurrently_sell() throws InterruptedException {
		// given
		long currentStock = 50;
		long sellStock = 10;
		int threadsCount = 10;

		Product product = Prepare.product(1000, currentStock);
		productRepository.save(product);

		// when
		CountDownLatch latch = new CountDownLatch(threadsCount);
		ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		for (int i = 0; i < threadsCount; i++) {
			executor.execute(() -> {
				try {
					productService.sell(new ProductSell(product.getId(), sellStock));
					successCount.incrementAndGet();
				} catch (InsufficientStockException e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		// then
		assertEquals(threadsCount / 2, successCount.get());
		assertEquals(threadsCount / 2, failCount.get());
	}
}
