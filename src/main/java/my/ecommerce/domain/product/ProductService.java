package my.ecommerce.domain.product;

import my.ecommerce.domain.product.dto.ProductList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    public ProductService() {
    }

    public ProductList findMany() {
        return new ProductList(2, List.of(
                Product.builder()
                        .id(1)
                        .name("product1")
                        .price(1000)
                        .stock(10)
                        .build(),
                Product.builder()
                        .id(1)
                        .name("product2")
                        .price(2000)
                        .stock(20)
                        .build()
        ));
    }
}
