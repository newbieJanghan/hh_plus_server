package my.ecommerce.domain.product.exceptions;

public class InsufficientStockException extends RuntimeException {
	public InsufficientStockException() {
		super("product stock is insufficient");
	}
}
