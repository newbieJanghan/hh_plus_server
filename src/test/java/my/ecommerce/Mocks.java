package my.ecommerce;

import my.ecommerce.domain.balance.Balance;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductList;

import java.util.List;

public class Mocks {
    public static Balance mockBalance(long amount) {
        return Balance.builder()
                .amount(amount)
                .build();
    }

    public static ProductList mockProductList() {
        return new ProductList(2, mockProducts());
    }

    public static List<Product> mockProducts() {
        return List.of(
                Product.builder()
                        .id(1)
                        .name("상품1")
                        .price(1000)
                        .build(),
                Product.builder()
                        .id(2)
                        .name("상품2")
                        .price(2000)
                        .build());
    }
}
