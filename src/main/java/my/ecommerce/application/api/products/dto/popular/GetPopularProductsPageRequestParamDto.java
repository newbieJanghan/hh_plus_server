package my.ecommerce.application.api.products.dto.popular;

import java.time.LocalDateTime;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.page.Sort;
import my.ecommerce.domain.product.dto.PopularProductsPeriodQueryDto;
import my.ecommerce.utils.Today;

@Getter
public class GetPopularProductsPageRequestParamDto extends GetProductsPageRequestParamDto {
	@Nullable
	private final LocalDateTime from;
	@Nullable
	private final LocalDateTime to;

	public GetPopularProductsPageRequestParamDto(
		Long size, Sort sort, String cursor, String category,
		@Nullable LocalDateTime from, @Nullable LocalDateTime to) {
		super(size, sort, cursor, category);
		this.from = from;
		this.to = to;
	}

	public static GetPopularProductsPageRequestParamDto empty() {
		return new GetPopularProductsPageRequestParamDto(0L, null, null, null, null, null);
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
