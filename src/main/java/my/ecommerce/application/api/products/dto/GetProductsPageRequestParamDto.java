package my.ecommerce.application.api.products.dto;


import lombok.Getter;
import my.ecommerce.application.page.CursorPageRequest;
import my.ecommerce.application.page.Sort;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.utils.UUIDGenerator;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Getter
public class GetProductsPageRequestParamDto extends CursorPageRequest {
    @Nullable
    private final String category;

    public GetProductsPageRequestParamDto(
            int size,
            @Nullable Sort sort,
            @Nullable String cursor,

            @Nullable String category) {
        super(size, sort, cursor);
        this.category = category;
    }

    public static GetProductsPageRequestParamDto empty() {
        return new GetProductsPageRequestParamDto(0, null, null, null);
    }

    public CursorPagedProductsQueryDto toCursorQueryDto() {
        UUID cursor = getCursor() == null ? null : UUIDGenerator.fromString(getCursor());

        return CursorPagedProductsQueryDto.builder().limit(getSize()).sort(getSort()).cursor(cursor).build();
    }
}
