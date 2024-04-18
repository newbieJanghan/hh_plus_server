package my.ecommerce.presentation.dto.response.page;

import java.util.List;

import lombok.Getter;
import my.ecommerce.presentation.dto.page.CursorPageInfo;
import my.ecommerce.presentation.dto.page.PageResponse;
import my.ecommerce.presentation.dto.response.ProductResponse;

@Getter
public class ProductsPageResponse extends PageResponse<ProductResponse, CursorPageInfo> {
	public ProductsPageResponse(List<ProductResponse> data, CursorPageInfo pageInfo) {
		super(data, pageInfo);
	}
}
