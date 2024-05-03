package my.ecommerce.domain.order.order_item;

import lombok.Builder;
import my.ecommerce.domain.product.Product;

@Builder
public record OrderItemCreate(Product product, long quantity, long currentPrice) {
}
