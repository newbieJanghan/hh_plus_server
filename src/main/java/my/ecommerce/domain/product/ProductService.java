package my.ecommerce.domain.product;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import my.ecommerce.domain.product.exceptions.InsufficientStockException;
import my.ecommerce.presentation.request.page.ProductPageRequest;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Page<Product> findAllWithPage(ProductPageRequest request) {
		return productRepository.findAllWithPage(request.toCursorQueryDto());
	}

	public Product getAvailableProduct(UUID id, Long quantity) {
		Product product = productRepository.findById(id);
		checkStock(product, quantity);

		return product;
	}

	public Product sell(Product product, Long quantity) {
		checkStock(product, quantity);
		product.sell(quantity);
		return productRepository.save(product);
	}

	private void checkStock(Product product, Long quantity) {
		if (!product.isAvailable(quantity)) {
			throw new InsufficientStockException();
		}
	}
}
