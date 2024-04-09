package my.ecommerce.domain.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class Product {
    private UUID id;
    private String name;
    private long price;
    private long stock;

    @Builder
    private Product(UUID id, String name, long price, long stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
