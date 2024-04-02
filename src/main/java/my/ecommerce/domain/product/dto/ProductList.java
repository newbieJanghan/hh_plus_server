package my.ecommerce.domain.product.dto;

import lombok.Getter;
import my.ecommerce.domain.product.Product;

import java.util.List;

@Getter
public class ProductList {
    private final long total;
    private final List<Product> list;

    public ProductList(long total, List<Product> list) {
        this.total = total;
        this.list = list;
    }

    public long getCursor() {
        return list.getLast().getId();
    }

    public long getSize() {
        return list.size();
    }
}
