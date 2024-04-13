package my.ecommerce.domain.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.presentation.page.Sort;

@Getter
@NoArgsConstructor
public class CursorPagedPopularProductsQueryDto {
	long limit;
	Sort sort;

	@Nullable
	UUID cursor;

	LocalDateTime from;
	LocalDateTime to;

	@Builder
	private CursorPagedPopularProductsQueryDto(long limit, Sort sort, @Nullable UUID cursor, LocalDateTime from,
		LocalDateTime to) {
		this.limit = limit;
		this.sort = sort;
		this.cursor = cursor;
		this.from = from;
		this.to = to;
	}
}
