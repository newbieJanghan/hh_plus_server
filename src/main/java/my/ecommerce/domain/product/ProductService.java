package my.ecommerce.domain.product;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	public Product getAvailableProduct(UUID productId, Long stock) {
		return new Product(productId, "Product", 1000, stock);
	}

	public void sell(Product product, Long quantity) {
	}
}
