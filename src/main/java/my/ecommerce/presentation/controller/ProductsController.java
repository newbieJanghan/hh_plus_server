package my.ecommerce.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.page.ProductPageService;
import my.ecommerce.domain.product.popular.PopularProduct;
import my.ecommerce.presentation.page.CursorPageInfo;
import my.ecommerce.presentation.request.page.PopularProductsPageRequestParams;
import my.ecommerce.presentation.request.page.ProductsPageRequestParams;
import my.ecommerce.presentation.response.PopularProductResponse;
import my.ecommerce.presentation.response.ProductResponseDto;
import my.ecommerce.presentation.response.page.PopularProductsPageResponse;
import my.ecommerce.presentation.response.page.ProductsPageResponse;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
	private final ProductPageService productsService;

	@Autowired
	public ProductsController(ProductPageService productsService) {
		this.productsService = productsService;
	}

	@GetMapping("")
	public ProductsPageResponse getProducts(@Valid @ModelAttribute ProductsPageRequestParams paramDto) {
		Page<Product> page = productsService.findAllWithPage(paramDto);
		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new ProductsPageResponse(page.map(ProductResponseDto::fromProduct).toList(), pageInfo);
	}

	@GetMapping("/popular")
	public PopularProductsPageResponse getPopularProducts(@Valid @ModelAttribute
	PopularProductsPageRequestParams paramDto) {
		Page<PopularProduct> page = productsService.findAllPopularWithPage(paramDto);

		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new PopularProductsPageResponse(
			page.map(PopularProductResponse::fromPopularProduct).toList(),
			pageInfo);
	}
}
