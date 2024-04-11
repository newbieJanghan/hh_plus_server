package my.ecommerce.application.api.orders.dto;

import org.hibernate.validator.constraints.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import my.ecommerce.domain.order.dto.CreateOrderItemDto;

@Getter
public class CreateOrderRequestOrderItemDto {
	@UUID
	private java.util.UUID productId;

	@NotNull
	@Min(1)
	private long quantity;

	public CreateOrderRequestOrderItemDto(java.util.UUID productId, long quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public CreateOrderItemDto toDomain() {
		return CreateOrderItemDto.builder()
			.productId(productId)
			.quantity(quantity)
			.build();
	}
}
