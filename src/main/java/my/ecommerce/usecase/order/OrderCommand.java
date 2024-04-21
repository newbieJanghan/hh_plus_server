package my.ecommerce.usecase.order;

import java.util.List;
import java.util.UUID;

public record OrderCommand(UUID userId, List<OrderItemCommand> items) {

	public record OrderItemCommand(UUID productId, long quantity, double currentPrice) {
	}
}

