package my.ecommerce.infrastructure.product;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import org.springframework.data.domain.Page;

public class ProductRepositoryImpl implements ProductRepository {
    public Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramsDto) {
        return null;
    }
}
