package my.ecommerce.application.api.products.dto;

import lombok.Getter;
import my.ecommerce.application.response.pagination.CursorPageInfo;
import my.ecommerce.application.response.pagination.PaginatedResponse;

import java.util.List;

@Getter
public class PaginatedProductsResponseDto extends PaginatedResponse<ProductResponseDto, CursorPageInfo> {
    public PaginatedProductsResponseDto(List<ProductResponseDto> data, CursorPageInfo pageInfo) {
        super(data, pageInfo);
    }
}
