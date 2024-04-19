package my.ecommerce.api.dto.request.page;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.api.dto.page.CursorPageRequest;
import my.ecommerce.api.dto.page.Sort;

@Getter
public class ProductPageRequest extends CursorPageRequest {
	@Nullable
	private final String category;

	public ProductPageRequest(
		int size,
		@Nullable Sort sort,
		@Nullable String cursor,

		@Nullable String category) {
		super(size, sort, cursor);
		this.category = category;
	}

	public ProductPageCursorQuery toCursorQueryDto() {
		return ProductPageCursorQuery
			.builder()
			.limit(getSize())
			.sort(getSort())
			.cursor(getUUIDCursor())
			.build();
	}
}
