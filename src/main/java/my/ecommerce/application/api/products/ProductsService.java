package my.ecommerce.application.api.products;

import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.application.page.CursorPageInfo;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductsPageResponseDto findAllWithPage(GetProductsPageRequestParamDto paramDto) {
        Page<Product> page = productRepository.findAllWithPage(paramDto.toCursorQueryDto());
        CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, getCursorFromPage(page));

        return new ProductsPageResponseDto(page.map(ProductResponseDto::new).toList(), pageInfo);
    }

    public ProductsPageResponseDto findPopularProductsWithPage() {
        return ProductsPageResponseDto.empty();
    }

    private String getCursorFromPage(Page<Product> page) {
        return page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
    }
}
