package my.ecommerce.domain.order.order_item;

import java.util.UUID;

public interface OrderItemRepository {
	void destroy(UUID id);
}
