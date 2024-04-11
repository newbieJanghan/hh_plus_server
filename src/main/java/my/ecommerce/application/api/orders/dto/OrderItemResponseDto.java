package my.ecommerce.application.api.orders.dto;

import java.util.UUID;

import lombok.Getter;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.order.order_item.OrderItemStatus;

@Getter
public class OrderItemResponseDto {
	private final UUID id;
	private final UUID orderId;
	private final ProductResponseDto product;
	private final long quantity;
	private final OrderItemStatus status;

	public OrderItemResponseDto(UUID id, UUID orderId, ProductResponseDto product, long quantity,
		OrderItemStatus status) {
		this.id = id;
		this.orderId = orderId;
		this.product = product;
		this.quantity = quantity;
		this.status = status;

	}

	public static OrderItemResponseDto fromOrderItem(OrderItem orderItem) {
		return new OrderItemResponseDto(
			orderItem.getId(),
			orderItem.getOrderId(),
			ProductResponseDto.fromProduct(orderItem.getProduct()),
			orderItem.getQuantity(),
			orderItem.getStatus());

	}
}
