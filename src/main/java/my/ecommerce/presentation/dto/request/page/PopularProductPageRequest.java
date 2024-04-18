package my.ecommerce.presentation.dto.request.page;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.domain.product.popular.PeriodQuery;
import my.ecommerce.presentation.dto.page.Sort;
import my.ecommerce.utils.Today;

@Getter
public class PopularProductPageRequest extends ProductPageRequest {
	@Nullable
	private final LocalDateTime from;
	@Nullable
	private final LocalDateTime to;

	public PopularProductPageRequest(
		int size, Sort sort, String cursor, String category,
		@Nullable LocalDateTime from, @Nullable LocalDateTime to) {
		super(size, sort, cursor, category);
		this.from = from;
		this.to = to;
	}

	public PeriodQuery toPopularPeriodQueryDto() {
		LocalDateTime from = this.from != null ? this.from : Today.beginningOfDay();
		LocalDateTime to = this.to != null ? this.to :
			LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 23, 59, 59);

		return PeriodQuery
			.builder()
			.from(from)
			.to(to)
			.build();
	}
}
