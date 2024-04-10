package my.ecommerce.domain.product;

import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
    Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramsDto);
}
