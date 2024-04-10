package my.ecommerce.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.application.page.Sort;
import org.springframework.lang.Nullable;

import java.util.UUID;

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
