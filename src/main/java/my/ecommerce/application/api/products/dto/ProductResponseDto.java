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

    public ProductResponseDto(UUID id, String name, long price, long stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static ProductResponseDto fromProduct(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }
}
