package my.ecommerce.domain.order;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import my.ecommerce.domain.order.order_item.OrderItemCreate;

@Builder
public record OrderCreate(UUID userId, List<OrderItemCreate> items) {

}
