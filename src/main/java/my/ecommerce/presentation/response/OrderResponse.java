package my.ecommerce.presentation.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemStatus;

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
			.map(OrderItemResponse::fromOrderItem)
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
		private final ProductResponseDto product;
		private final long quantity;
		private final OrderItemStatus status;

		public OrderItemResponse(UUID id, UUID orderId, ProductResponseDto product, long quantity,
			OrderItemStatus status) {
			this.id = id;
			this.orderId = orderId;
			this.product = product;
			this.quantity = quantity;
			this.status = status;

		}

		public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
			return new OrderItemResponse(
				orderItem.getId(),
				orderItem.getOrderId(),
				ProductResponseDto.fromProduct(orderItem.getProduct()),
				orderItem.getQuantity(),
				orderItem.getStatus());

		}
	}
}
