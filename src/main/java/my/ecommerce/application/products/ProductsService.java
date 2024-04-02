package my.ecommerce.application.products;

import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.application.products.dto.GetProductsRequestParamDto;
import my.ecommerce.application.products.dto.PaginatedProductsResponseDto;
import my.ecommerce.application.products.dto.ProductsResponseDto;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    public ProductsService() {
    }

    public PaginatedProductsResponseDto findMany(GetProductsRequestParamDto paramDto) {
        ProductSearchResult result = new ProductSearchResult(1, List.of(Product.builder().id(1).build()));
        CursorPageInfo pageInfo = CursorPageInfo.builder().build();

        return new PaginatedProductsResponseDto(result.getList(), pageInfo);
    }

    public ProductsResponseDto findPopularProducts() {
        ProductSearchResult result = new ProductSearchResult(1, List.of(Product.builder().id(1).build()));

        return new ProductsResponseDto(result.getList());
    }
}
