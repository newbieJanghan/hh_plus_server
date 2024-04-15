package my.ecommerce.presentation.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.presentation.page.CursorPageInfo;
import my.ecommerce.presentation.page.PageResponse;
import my.ecommerce.presentation.response.ProductResponseDto;

@Getter
public class ProductsPageResponse extends PageResponse<ProductResponseDto, CursorPageInfo> {
	public ProductsPageResponse(List<ProductResponseDto> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}

	public static ProductsPageResponse empty() {
		return new ProductsPageResponse(List.of(), CursorPageInfo.empty());
	}
}