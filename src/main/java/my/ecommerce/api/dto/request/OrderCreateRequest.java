package my.ecommerce.api.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import my.ecommerce.usecase.order.OrderCommand;

@Getter
public class OrderCreateRequest {
	@Valid
	@NotEmpty
	private List<OrderItemCreateRequest> items;

	public OrderCreateRequest(List<OrderItemCreateRequest> items) {
		this.items = items;
	}

	public OrderCommand toCommand(UUID userId) {
		return new OrderCommand(userId, items.stream().map(OrderItemCreateRequest::toCommand).toList());
	}

	@Getter
	public static class OrderItemCreateRequest {
		@org.hibernate.validator.constraints.UUID
		private UUID productId;

		@NotNull
		@Min(1)
		private long quantity;

		@NotNull
		@Min(1)
		private long currentPrice;

		public OrderItemCreateRequest(UUID productId, long quantity, long currentPrice) {
			this.productId = productId;
			this.quantity = quantity;
			this.currentPrice = currentPrice;
		}

		public OrderCommand.OrderItemCommand toCommand() {
			return new OrderCommand.OrderItemCommand(productId, quantity, currentPrice);
		}
	}
}
