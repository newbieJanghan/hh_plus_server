package my.ecommerce.presentation.request.page;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.domain.product.popular.PopularProductsPeriodQueryDto;
import my.ecommerce.presentation.page.Sort;
import my.ecommerce.utils.Today;

@Getter
public class PopularProductsPageRequestParams extends ProductsPageRequestParams {
	@Nullable
	private final LocalDateTime from;
	@Nullable
	private final LocalDateTime to;

	public PopularProductsPageRequestParams(
		Long size, Sort sort, String cursor, String category,
		@Nullable LocalDateTime from, @Nullable LocalDateTime to) {
		super(size, sort, cursor, category);
		this.from = from;
		this.to = to;
	}

	public static PopularProductsPageRequestParams empty() {
		return new PopularProductsPageRequestParams(0L, null, null, null, null, null);
	}

	public PopularProductsPeriodQueryDto toPopularPeriodQueryDto() {
		LocalDateTime from = this.from != null ? this.from : Today.beginningOfDay();
		LocalDateTime to = this.to != null ? this.to :
			LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 23, 59, 59);

		return PopularProductsPeriodQueryDto
			.builder()
			.from(from)
			.to(to)
			.build();
	}
}
