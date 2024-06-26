package my.ecommerce.domain.product;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import my.ecommerce.domain.product.dto.PeriodQuery;
import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.domain.product.dto.ProductSell;
import my.ecommerce.domain.product.exceptions.InsufficientStockException;

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

	public Page<Product> getPopularProductPage(ProductPageCursorQuery query, PeriodQuery period) {
		return productRepository.findAllPopularWithPage(query, period);
	}

	public Product findById(UUID id) {
		return productRepository.findById(id);
	}

	@Transactional
	public Product sell(ProductSell sell) {
		Product product = productRepository.findByIdForUpdate(sell.id());
		checkStock(product, sell.quantity());
		product.sell(sell.quantity());
		return productRepository.save(product);
	}

	public List<Product> sellMany(List<ProductSell> sells) {
		return sells.stream().map(this::sell).toList();
	}

	private void checkStock(Product product, Long quantity) {
		if (!product.isAvailable(quantity)) {
			throw new InsufficientStockException();
		}
	}
}
