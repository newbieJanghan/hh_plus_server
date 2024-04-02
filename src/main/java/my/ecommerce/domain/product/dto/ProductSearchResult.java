package my.ecommerce.domain.product.dto;

import lombok.Getter;
import my.ecommerce.domain.product.Product;

import java.util.List;

@Getter
public class ProductSearchResult {
    private final long total;
    private final List<Product> list;

    public ProductSearchResult(long total, List<Product> list) {
        this.total = total;
        this.list = list;
    }
}
