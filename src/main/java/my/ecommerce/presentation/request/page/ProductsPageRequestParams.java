package my.ecommerce.presentation.request.page;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.presentation.page.CursorPageRequestParams;
import my.ecommerce.presentation.page.Sort;

@Getter
public class ProductsPageRequestParams extends CursorPageRequestParams {
	@Nullable
	private final String category;

	public ProductsPageRequestParams(
		@Nullable Long size,
		@Nullable Sort sort,
		@Nullable String cursor,

		@Nullable String category) {
		super(size, sort, cursor);
		this.category = category;
	}

	public static ProductsPageRequestParams empty() {
		return new ProductsPageRequestParams(0L, null, null, null);
	}

	public CursorPagedProductsQueryDto toCursorQueryDto() {
		return CursorPagedProductsQueryDto
			.builder()
			.limit(getSize())
			.sort(getSort())
			.cursor(getUUIDCursor())
			.build();
	}
}
