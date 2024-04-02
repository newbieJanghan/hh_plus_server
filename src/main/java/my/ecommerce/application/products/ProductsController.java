package my.ecommerce.application.products;

import my.ecommerce.application.products.dto.GetProductsRequestParamDto;
import my.ecommerce.application.products.dto.PaginatedProductsResponseDto;
import my.ecommerce.application.products.dto.ProductsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("")
    public PaginatedProductsResponseDto getProducts(@ModelAttribute GetProductsRequestParamDto paramDto) {
        return productsService.findMany(paramDto);
    }

    @GetMapping("/popular")
    public ProductsResponseDto getPopularProducts() {
        return productsService.findPopularProducts();
    }
}
