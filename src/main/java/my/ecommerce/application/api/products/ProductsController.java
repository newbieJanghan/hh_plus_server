package my.ecommerce.application.api.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.application.api.products.dto.popular.GetPopularProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.popular.PopularProductsPageResponseDto;

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
	public PopularProductsPageResponseDto getPopularProducts(@Valid @ModelAttribute
	GetPopularProductsPageRequestParamDto paramDto) {
		return productsService.findPopularProductsWithPage(paramDto);
	}
}
