package my.ecommerce.application.page;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


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
            @Nullable String cursor
    ) {
        this.size = size;
        this.totalCounts = totalCounts;

        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalCounts / size);
        this.cursor = cursor;
    }

    public static CursorPageInfo empty() {
        return new CursorPageInfo(0, 0, 0, null);
    }

}
