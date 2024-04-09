package my.ecommerce.application.api.products.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class GetProductsRequestParamDto {
    @Nullable
    private long limit = 10;

    @Nullable
    @org.hibernate.validator.constraints.UUID(allowEmpty = true)
    private UUID cursor;

    @Nullable
    private String sort;
    @Nullable
    private String direction;
    @Nullable
    private String category;
}
