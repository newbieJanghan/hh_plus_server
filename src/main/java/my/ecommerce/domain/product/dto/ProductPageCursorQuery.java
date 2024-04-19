package my.ecommerce.domain.product.dto;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.api.dto.page.Sort;

@Getter
@NoArgsConstructor
public class ProductPageCursorQuery {
	int limit;
	Sort sort;

	@Nullable
	UUID cursor;
	@Nullable
	String category;

	@Builder
	public ProductPageCursorQuery(int limit, Sort sort, @Nullable UUID cursor, @Nullable String category) {
		this.limit = limit;
		this.sort = sort;
		this.cursor = cursor;
		this.category = category;
	}
}
