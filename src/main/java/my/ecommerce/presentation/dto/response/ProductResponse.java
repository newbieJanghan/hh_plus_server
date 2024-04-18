package my.ecommerce.presentation.dto.response;

import java.util.UUID;

import lombok.Getter;
import my.ecommerce.domain.product.Product;

@Getter
public class ProductResponse {
	private final UUID id;
	private final String name;
	private final long price;
	private final long stock;

	public ProductResponse(UUID id, String name, long price, long stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public static ProductResponse fromProduct(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
	}
}
