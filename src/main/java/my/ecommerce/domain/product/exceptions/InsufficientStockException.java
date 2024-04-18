package my.ecommerce.domain.product.exceptions;

import my.ecommerce.utils.CustomException;

public class InsufficientStockException extends CustomException {
	public InsufficientStockException() {
		super("product stock is insufficient", "INSUFFICIENT_STOCK", 422, LogLevel.INFO, null);
	}
}
