package my.ecommerce.domain.order.dto;

import lombok.Builder;
import my.ecommerce.domain.product.Product;

@Builder
public record OrderItemCreate(Product product, long quantity, long currentPrice) {

}
