package my.ecommerce.application.api.orders.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.order.Order;

@Getter
public class OrderResponseDto {
	private final UUID id;
	private final UUID userId;
	private final long totalPrice;

	private final List<OrderItemResponseDto> items;

	@Builder
	public OrderResponseDto(UUID id, UUID userId, long totalPrice, List<OrderItemResponseDto> items) {
		this.id = id;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}

	public static OrderResponseDto fromDomain(Order order) {
		List<OrderItemResponseDto> itemsResponseDto = order.getItems()
			.stream()
			.map(OrderItemResponseDto::fromOrderItem)
			.toList();
		return OrderResponseDto.builder()
			.id(order.getId())
			.userId(order.getUserId())
			.totalPrice(order.getTotalPrice())
			.items(itemsResponseDto)
			.build();
	}
}
