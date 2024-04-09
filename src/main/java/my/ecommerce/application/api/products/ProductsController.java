package my.ecommerce.application.api.products;

import jakarta.validation.Valid;
import my.ecommerce.application.api.products.dto.GetProductsRequestParamDto;
import my.ecommerce.application.api.products.dto.PaginatedProductsResponseDto;
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
    public PaginatedProductsResponseDto getProducts(@Valid @ModelAttribute GetProductsRequestParamDto paramDto) {
        return productsService.findMany(paramDto);
    }

    @GetMapping("/popular")
    public PaginatedProductsResponseDto getPopularProducts() {
        return productsService.findPopularProducts();
    }
}
