package my.ecommerce.application.products;

import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.application.products.dto.ProductsResponseDto;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    public ProductsService() {
    }

    public ProductsResponseDto findMany() {
        ProductSearchResult result = new ProductSearchResult(2, List.of(
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

        CursorPageInfo pageInfo = CursorPageInfo.builder()
                .total(result.getTotal())
                .cursor(result.getList().getLast().getId())
                .size(result.getList().size())
                .build();

        return new ProductsResponseDto(result.getList(), pageInfo);
    }
}
