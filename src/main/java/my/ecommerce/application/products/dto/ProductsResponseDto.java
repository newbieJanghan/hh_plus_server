package my.ecommerce.application.products.dto;

import lombok.Builder;
import lombok.Getter;
import my.ecommerce.application.common.PaginatedResponse;
import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.domain.product.Product;

import java.util.List;

@Getter
public class ProductsResponseDto extends PaginatedResponse<Product, CursorPageInfo> {
    @Builder
    public ProductsResponseDto(List<Product> data, CursorPageInfo pageInfo) {
        super("OK", data, pageInfo);
    }
}
