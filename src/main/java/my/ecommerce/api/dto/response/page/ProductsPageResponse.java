package my.ecommerce.api.dto.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.api.dto.page.CursorPageInfo;
import my.ecommerce.api.dto.page.PageResponse;
import my.ecommerce.api.dto.response.ProductResponse;

@Getter
public class ProductsPageResponse extends PageResponse<ProductResponse, CursorPageInfo> {
	public ProductsPageResponse(List<ProductResponse> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}
}
