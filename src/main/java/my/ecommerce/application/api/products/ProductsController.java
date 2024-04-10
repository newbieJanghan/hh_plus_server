package my.ecommerce.application.api.products;

import jakarta.validation.Valid;
import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
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
    public ProductsPageResponseDto getProducts(@Valid @ModelAttribute GetProductsPageRequestParamDto paramDto) {
        return productsService.findAllWithPage(paramDto);
    }

    @GetMapping("/popular")
    public ProductsPageResponseDto getPopularProducts() {
        return productsService.findPopularProductsWithPage();
    }
}
