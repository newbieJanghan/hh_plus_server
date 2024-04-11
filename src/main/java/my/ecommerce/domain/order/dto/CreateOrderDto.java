package my.ecommerce.domain.order.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderDto {
	private UUID userId;
	private long totalPrice;
	private List<CreateOrderItemDto> items;

	@Builder
	private CreateOrderDto(UUID userId, long totalPrice, List<CreateOrderItemDto> items) {
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}
}
