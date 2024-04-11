package my.ecommerce.application.api.products.dto.popular;

import lombok.Getter;
import my.ecommerce.application.page.CursorPageInfo;
import my.ecommerce.application.page.PageResponse;

import java.util.List;

@Getter
public class PopularProductsPageResponseDto extends PageResponse<PopularProductResponseDto, CursorPageInfo> {
    public PopularProductsPageResponseDto(List<PopularProductResponseDto> data, CursorPageInfo pageInfo) {
        super(data, pageInfo);
    }

    public static PopularProductsPageResponseDto empty() {
        return new PopularProductsPageResponseDto(List.of(), CursorPageInfo.empty());
    }
}
