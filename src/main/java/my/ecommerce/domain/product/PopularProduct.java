package my.ecommerce.domain.product;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PopularProduct extends Product {
    private final UUID id;
    private final String name;
    private final long price;
    private final long stock;
    private final int soldAmountInPeriod;

    public PopularProduct(UUID id, String name, long price, long stock, int soldAmountInPeriod) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.soldAmountInPeriod = soldAmountInPeriod;
    }
}
