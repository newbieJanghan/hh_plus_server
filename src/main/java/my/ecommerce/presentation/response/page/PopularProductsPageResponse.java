package my.ecommerce.presentation.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.presentation.page.CursorPageInfo;
import my.ecommerce.presentation.page.PageResponse;
import my.ecommerce.presentation.response.PopularProductResponse;

@Getter
public class PopularProductsPageResponse extends PageResponse<PopularProductResponse, CursorPageInfo> {
	public PopularProductsPageResponse(List<PopularProductResponse> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}

	public static PopularProductsPageResponse empty() {
		return new PopularProductsPageResponse(List.of(), CursorPageInfo.empty());
	}
}
