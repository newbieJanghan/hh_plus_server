package my.ecommerce.application.api.products.dto;

import lombok.Getter;
import my.ecommerce.application.page.CursorPageInfo;
import my.ecommerce.application.page.PageResponse;

import java.util.List;

@Getter
public class ProductsPageResponseDto extends PageResponse<ProductResponseDto, CursorPageInfo> {
    public ProductsPageResponseDto(List<ProductResponseDto> data, CursorPageInfo pageInfo) {
        super(data, pageInfo);
    }
}
