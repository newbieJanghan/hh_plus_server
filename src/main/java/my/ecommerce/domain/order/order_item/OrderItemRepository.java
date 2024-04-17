package my.ecommerce.domain.order.order_item;

import java.util.UUID;

public interface OrderItemRepository {
	OrderItem save(OrderItem orderItem);

	void destroy(UUID id);
}
