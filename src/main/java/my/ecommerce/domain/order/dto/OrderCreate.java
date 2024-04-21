package my.ecommerce.domain.order.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record OrderCreate(UUID userId, List<OrderItemCreate> items) {

}
