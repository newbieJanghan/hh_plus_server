package my.ecommerce.application.api.products.dto;

import lombok.Getter;
import my.ecommerce.domain.product.Product;

import java.util.UUID;

@Getter
public class ProductResponseDto {
    private final UUID id;
    private final String name;
    private final long price;
    private final long stock;

    public ProductResponseDto(Product product) {
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        stock = product.getStock();
    }
}
