package my.ecommerce.application.products;

import my.ecommerce.application.common.pagination.CursorPageInfo;
import my.ecommerce.application.products.dto.ProductsResponseDto;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.domain.product.dto.ProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ProductsResponseDto getProducts() {
        ProductList list = productService.findMany();
        CursorPageInfo pageInfo = CursorPageInfo.builder()
                .total(list.getTotal())
                .cursor(list.getCursor())
                .size(list.getSize())
                .build();
        return ProductsResponseDto.builder()
                .data(list.getList())
                .pageInfo(pageInfo)
                .build();
    }
}
