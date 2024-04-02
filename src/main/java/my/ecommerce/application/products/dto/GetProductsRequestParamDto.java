package my.ecommerce.application.products.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class GetProductsRequestParamDto {
    private long limit = 10;

    @Nullable
    private long cursor;

    @Nullable
    private String sort;

    @Nullable
    private String direction;
    
    @Nullable
    private String category;
}
