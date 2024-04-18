package my.ecommerce.presentation.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.presentation.page.CursorPageInfo;
import my.ecommerce.presentation.page.PageResponse;
import my.ecommerce.presentation.response.ProductResponse;

@Getter
public class ProductsPageResponse extends PageResponse<ProductResponse, CursorPageInfo> {
	public ProductsPageResponse(List<ProductResponse> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}
}
