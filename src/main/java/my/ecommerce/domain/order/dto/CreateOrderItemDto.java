package my.ecommerce.domain.order.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderItemDto {
	private UUID productId;
	private long quantity;

	@Builder
	private CreateOrderItemDto(UUID productId, long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
