package my.ecommerce.application.api.products.dto;


import lombok.Getter;
import my.ecommerce.application.page.CursorPageRequest;
import my.ecommerce.application.page.Sort;
import org.springframework.lang.Nullable;

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
}
