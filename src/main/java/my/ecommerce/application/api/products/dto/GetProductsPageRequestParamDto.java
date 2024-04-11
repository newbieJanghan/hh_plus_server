package my.ecommerce.application.api.products.dto;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.application.page.CursorPageRequest;
import my.ecommerce.application.page.Sort;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;

@Getter
public class GetProductsPageRequestParamDto extends CursorPageRequest {
	@Nullable
	private final String category;

	public GetProductsPageRequestParamDto(
		@Nullable Long size,
		@Nullable Sort sort,
		@Nullable String cursor,

		@Nullable String category) {
		super(size, sort, cursor);
		this.category = category;
	}

	public static GetProductsPageRequestParamDto empty() {
		return new GetProductsPageRequestParamDto(0L, null, null, null);
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
