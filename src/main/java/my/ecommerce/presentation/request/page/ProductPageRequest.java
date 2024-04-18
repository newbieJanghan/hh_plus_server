package my.ecommerce.presentation.request.page;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.presentation.page.CursorPageRequest;
import my.ecommerce.presentation.page.Sort;

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
