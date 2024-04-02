package my.ecommerce.application.common.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CursorPageInfo {
    private long total;
    private long cursor;
    private long totalPages;
    private long size;

    @Builder
    private CursorPageInfo(long total, long cursor, long size) {
        this.total = total;
        this.cursor = cursor;
        this.size = size;
        this.totalPages = (long) Math.ceil((double) total / size);
    }
}
