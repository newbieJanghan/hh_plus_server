package my.ecommerce.application.api.orders.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import my.ecommerce.domain.order.dto.CreateOrderDto;

@Getter
public class CreateOrderRequestDto {
	@Valid
	@NotEmpty
	private List<CreateOrderRequestOrderItemDto> items;

	@NotNull
	@Min(0)
	private long totalPrice;

	public CreateOrderRequestDto(List<CreateOrderRequestOrderItemDto> items, long totalPrice) {
		this.items = items;
		this.totalPrice = totalPrice;
	}

	public CreateOrderDto toDomain(UUID userId) {
		return CreateOrderDto.builder()
			.userId(userId)
			.items(items.stream().map(CreateOrderRequestOrderItemDto::toDomain).toList())
			.totalPrice(totalPrice)
			.build();
	}
}
