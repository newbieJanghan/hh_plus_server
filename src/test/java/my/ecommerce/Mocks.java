package my.ecommerce;

import my.ecommerce.application.balance.dto.BalanceResponseDto;
import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.application.products.dto.ProductsResponseDto;
import my.ecommerce.domain.balance.Balance;
import my.ecommerce.domain.product.Product;

import java.util.List;

public class Mocks {
    public static Balance mockBalance(long amount) {
        return Balance.builder()
                .amount(amount)
                .build();
    }

    public static BalanceResponseDto mockBalanceResponseDto(long amount) {
        return new BalanceResponseDto(mockBalance(amount));
    }

    public static ProductsResponseDto mockProductsResponseDto() {
        CursorPageInfo pageInfo = CursorPageInfo.builder()
                .total(2)
                .cursor(2)
                .size(2)
                .build();
        return new ProductsResponseDto(mockProducts(), pageInfo);
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
