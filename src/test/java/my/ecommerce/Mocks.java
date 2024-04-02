package my.ecommerce;

import my.ecommerce.application.balance.dto.BalanceResponseDto;
import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.application.products.dto.PaginatedProductsResponseDto;
import my.ecommerce.domain.balance.Balance;
import my.ecommerce.domain.product.Product;

import java.util.List;

public class Mocks {
    public static Balance mockBalance(long amount) {
        return Balance.builder()
                .amount(amount)
                .build();
    }

    public static BalanceResponseDto mockBalanceResponseDto() {
        return new BalanceResponseDto(new Balance());
    }

    public static PaginatedProductsResponseDto mockProductsResponseDto() {
        CursorPageInfo pageInfo = new CursorPageInfo();
        return new PaginatedProductsResponseDto(List.of(new Product()), pageInfo);
    }

    public static Product mockProduct(long id) {
        return Product.builder()
                .id(id)
                .build();
    }

    public static List<Product> mockProducts(long id) {
        return List.of(mockProduct(id));
    }
}
