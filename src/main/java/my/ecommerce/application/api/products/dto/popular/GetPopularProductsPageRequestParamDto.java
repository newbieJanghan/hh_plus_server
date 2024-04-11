package my.ecommerce.application.api.products.dto.popular;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.application.page.CursorPageRequest;
import my.ecommerce.application.page.Sort;
import my.ecommerce.application.utils.Today;
import my.ecommerce.domain.product.dto.CursorPagedPopularProductsQueryDto;

@Getter
public class GetPopularProductsPageRequestParamDto extends CursorPageRequest {
	@Nullable
	private final LocalDateTime from;
	@Nullable
	private final LocalDateTime to;

	public GetPopularProductsPageRequestParamDto(Long size, Sort sort, String cursor, @Nullable LocalDateTime from,
		@Nullable LocalDateTime to) {
		super(size, sort, cursor);
		this.from = from;
		this.to = to;
	}

	public static GetPopularProductsPageRequestParamDto empty() {
		return new GetPopularProductsPageRequestParamDto(0L, null, null, null, null);
	}

	public CursorPagedPopularProductsQueryDto toCursorQueryDto() {
		LocalDateTime from = this.from != null ? this.from : Today.beginningOfDay();
		LocalDateTime to = this.to != null ? this.to :
			LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 23, 59, 59);

		return CursorPagedPopularProductsQueryDto
			.builder()
			.limit(getSize())
			.sort(getSort())
			.cursor(getUUIDCursor())
			.from(from)
			.to(to)
			.build();
	}
}
