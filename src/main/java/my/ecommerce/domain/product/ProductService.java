package my.ecommerce.domain.product;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import my.ecommerce.api.dto.request.page.PopularProductPageRequest;
import my.ecommerce.domain.product.dto.PeriodQuery;
import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.domain.product.exceptions.InsufficientStockException;
import my.ecommerce.api.dto.request.page.ProductPageRequest;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Page<Product> getProductPage(ProductPageCursorQuery query) {
		return productRepository.findAllWithPage(query);
	}

	// public Product getAvailableProduct(UUID id, Long quantity) {
	// 	Product product = productRepository.findById(id);
	// 	checkStock(product, quantity);
	//
	// 	return product;
	// }

	public Page<Product> getPopularProductPage(ProductPageCursorQuery query, PeriodQuery period) {
		return productRepository.findAllPopularWithPage(query, period);
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
