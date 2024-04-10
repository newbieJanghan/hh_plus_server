package my.ecommerce.application.api.products;

import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.application.page.CursorPageInfo;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.dto.ProductSearchResult;
import my.ecommerce.utils.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    public ProductsService() {
    }

    public ProductsPageResponseDto findAllWithPage(GetProductsPageRequestParamDto paramDto) {
        ProductSearchResult result = new ProductSearchResult(1, List.of(Product.builder().id(UUIDGenerator.generate()).build()));
        CursorPageInfo pageInfo = CursorPageInfo.builder().build();

        return new ProductsPageResponseDto(result.getList().stream().map(ProductResponseDto::new).toList(), pageInfo);
    }

    public ProductsPageResponseDto findPopularProductsWithPage() {
        ProductSearchResult result = new ProductSearchResult(1, List.of(Product.builder().id(UUIDGenerator.generate()).build()));
        CursorPageInfo pageInfo = CursorPageInfo.builder().build();

        return new ProductsPageResponseDto(result.getList().stream().map(ProductResponseDto::new).toList(), pageInfo);
    }
}
