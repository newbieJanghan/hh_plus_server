package my.ecommerce.application.api.products;

import my.ecommerce.application.api.products.dto.GetProductsRequestParamDto;
import my.ecommerce.application.api.products.dto.PaginatedProductsResponseDto;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.application.response.pagination.CursorPageInfo;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSearchResult;
import my.ecommerce.utils.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    public ProductsService() {
    }

    public PaginatedProductsResponseDto findMany(GetProductsRequestParamDto paramDto) {
        ProductSearchResult result = new ProductSearchResult(1, List.of(Product.builder().id(UUIDGenerator.generate()).build()));
        CursorPageInfo pageInfo = CursorPageInfo.builder().build();

        return new PaginatedProductsResponseDto(result.getList().stream().map(ProductResponseDto::new).toList(), pageInfo);
    }

    public PaginatedProductsResponseDto findPopularProducts() {
        ProductSearchResult result = new ProductSearchResult(1, List.of(Product.builder().id(UUIDGenerator.generate()).build()));
        CursorPageInfo pageInfo = CursorPageInfo.builder().build();

        return new PaginatedProductsResponseDto(result.getList().stream().map(ProductResponseDto::new).toList(), pageInfo);
    }
}
