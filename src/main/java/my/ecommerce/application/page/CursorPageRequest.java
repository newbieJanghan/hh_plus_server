package my.ecommerce.application.page;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class CursorPageRequest implements PageRequest {

    private final int size;
    @Nullable
    private final Sort sort;

    @Nullable
    private final String cursor;

    public CursorPageRequest(int size, @Nullable Sort sort, @Nullable String cursor) {
        this.size = Math.max(size, 10);
        this.sort = sort;
        this.cursor = cursor;
    }

}

