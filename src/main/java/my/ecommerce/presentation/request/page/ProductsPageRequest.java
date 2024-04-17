package my.ecommerce.presentation.request.page;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.presentation.page.CursorPageRequest;
import my.ecommerce.presentation.page.Sort;

@Getter
public class ProductsPageRequest extends CursorPageRequest {
	@Nullable
	private final String category;

	public ProductsPageRequest(
		int size,
		@Nullable Sort sort,
		@Nullable String cursor,

		@Nullable String category) {
		super(size, sort, cursor);
		this.category = category;
	}

	public static ProductsPageRequest empty() {
		return new ProductsPageRequest(0, null, null, null);
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
