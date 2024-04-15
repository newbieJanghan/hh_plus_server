package my.ecommerce.domain.product.page;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.presentation.page.Sort;

@Getter
@NoArgsConstructor
public class CursorPagedProductsQueryDto {
	long limit;
	Sort sort;

	@Nullable
	UUID cursor;
	@Nullable
	String category;

	@Builder
	private CursorPagedProductsQueryDto(long limit, Sort sort, @Nullable UUID cursor, @Nullable String category) {
		this.limit = limit;
		this.sort = sort;
		this.cursor = cursor;
		this.category = category;
	}
}
