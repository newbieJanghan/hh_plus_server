package my.ecommerce.domain.product.dto;

import lombok.Getter;

@Getter
public class FindManyProductsParams {
    long limit;
    long cursor;
    String sort;
    String direction;
    String category;
}
