package my.ecommerce.presentation.dto.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.presentation.dto.page.CursorPageInfo;
import my.ecommerce.presentation.dto.page.PageResponse;
import my.ecommerce.presentation.dto.response.PopularProductResponse;

@Getter
public class PopularProductsPageResponse extends PageResponse<PopularProductResponse, CursorPageInfo> {
	public PopularProductsPageResponse(List<PopularProductResponse> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}
}
