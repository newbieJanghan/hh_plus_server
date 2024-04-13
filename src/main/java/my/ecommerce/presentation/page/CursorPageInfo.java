package my.ecommerce.presentation.page;

import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CursorPageInfo implements PageInfo {
	private int size;
	private int totalCounts;

	private int currentPage;
	private int totalPages;

	@Nullable
	private String cursor;

	@Builder
	private CursorPageInfo(
		int size,
		int totalCounts,
		int currentPage,
		int totalPages,
		@Nullable String cursor
	) {
		this.size = size;
		this.totalCounts = totalCounts;

		this.currentPage = currentPage;
		this.totalPages = totalPages;
		this.cursor = cursor;
	}

	public static CursorPageInfo empty() {
		return new CursorPageInfo(0, 0, 0, 1, null);
	}

	public static CursorPageInfo fromPage(Page page, @Nullable String cursor) {
		return CursorPageInfo.builder()
			.size(page.getNumberOfElements())
			.totalCounts((int)page.getTotalElements())
			.currentPage(page.getNumber())
			.totalPages(page.getTotalPages())
			.cursor(cursor)
			.build();
	}

}
