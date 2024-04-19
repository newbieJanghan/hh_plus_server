package my.ecommerce.api.dto.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.api.dto.page.CursorPageInfo;
import my.ecommerce.api.dto.page.PageResponse;
import my.ecommerce.api.dto.response.PopularProductResponse;

@Getter
public class PopularProductsPageResponse extends PageResponse<PopularProductResponse, CursorPageInfo> {
	public PopularProductsPageResponse(List<PopularProductResponse> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}
}
