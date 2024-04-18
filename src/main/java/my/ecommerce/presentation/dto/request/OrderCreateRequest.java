package my.ecommerce.presentation.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import my.ecommerce.domain.order.dto.CreateOrderDto;
import my.ecommerce.domain.order.dto.CreateOrderItemDto;

@Getter
public class OrderCreateRequest {
	@Valid
	@NotEmpty
	private List<OrderItemCreateRequest> items;

	@NotNull
	@Min(0)
	private long totalPrice;

	public OrderCreateRequest(List<OrderItemCreateRequest> items, long totalPrice) {
		this.items = items;
		this.totalPrice = totalPrice;
	}

	public CreateOrderDto toDomain(UUID userId) {
		return CreateOrderDto.builder()
			.userId(userId)
			.items(items.stream().map(OrderItemCreateRequest::toDomain).toList())
			.totalPrice(totalPrice)
			.build();
	}

	@Getter
	public static class OrderItemCreateRequest {
		@org.hibernate.validator.constraints.UUID
		private UUID productId;

		@NotNull
		@Min(1)
		private long quantity;

		public OrderItemCreateRequest(UUID productId, long quantity) {
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
}
