package my.ecommerce.application.products.dto;

import lombok.Getter;
import my.ecommerce.application.common.Response;
import my.ecommerce.domain.product.Product;

import java.util.List;

@Getter
public class ProductsResponseDto extends Response<List<Product>> {
    public ProductsResponseDto(List<Product> data) {
        super("OK", data);
    }
}
