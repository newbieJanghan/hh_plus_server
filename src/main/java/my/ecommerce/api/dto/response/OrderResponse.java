package my.ecommerce.api.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;

@Getter
public class OrderResponse {
	private final UUID id;
	private final UUID userId;
	private final long totalPrice;

	private final List<OrderItemResponse> items;

	@Builder
	public OrderResponse(UUID id, UUID userId, long totalPrice, List<OrderItemResponse> items) {
		this.id = id;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.items = items;
	}

	public static OrderResponse fromDomain(Order order) {
		List<OrderItemResponse> itemsResponseDto = order.getItems()
			.stream()
			.map(OrderItemResponse::fromDomain)
			.toList();
		return OrderResponse.builder()
			.id(order.getId())
			.userId(order.getUserId())
			.totalPrice(order.getTotalPrice())
			.items(itemsResponseDto)
			.build();
	}

	@Getter
	public static class OrderItemResponse {
		private final UUID id;
		private final UUID orderId;
		private final ProductResponse product;
		private final long quantity;
		private final OrderItem.OrderItemStatus status;

		public OrderItemResponse(UUID id, UUID orderId, ProductResponse product, long quantity,
			OrderItem.OrderItemStatus status) {
			this.id = id;
			this.orderId = orderId;
			this.product = product;
			this.quantity = quantity;
			this.status = status;

		}

		public static OrderItemResponse fromDomain(OrderItem orderItem) {
			return new OrderItemResponse(
				orderItem.getId(),
				orderItem.getOrder().getId(),
				ProductResponse.fromProduct(orderItem.getProduct()),
				orderItem.getQuantity(),
				orderItem.getStatus());

		}
	}
}
