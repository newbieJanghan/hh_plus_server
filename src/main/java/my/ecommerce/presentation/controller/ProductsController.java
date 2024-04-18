package my.ecommerce.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductService;
import my.ecommerce.domain.product.popular.PopularProduct;
import my.ecommerce.domain.product.popular.PopularProductService;
import my.ecommerce.presentation.page.CursorPageInfo;
import my.ecommerce.presentation.request.page.PopularProductPageRequest;
import my.ecommerce.presentation.request.page.ProductPageRequest;
import my.ecommerce.presentation.response.PopularProductResponse;
import my.ecommerce.presentation.response.ProductResponse;
import my.ecommerce.presentation.response.page.PopularProductsPageResponse;
import my.ecommerce.presentation.response.page.ProductsPageResponse;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
	private final ProductService productsService;
	private final PopularProductService popularProductService;

	@Autowired
	public ProductsController(ProductService productsService, PopularProductService popularProductService) {
		this.productsService = productsService;
		this.popularProductService = popularProductService;
	}

	@GetMapping("")
	public ProductsPageResponse getProducts(@Valid @ModelAttribute ProductPageRequest paramDto) {
		Page<Product> page = productsService.findAllWithPage(paramDto);
		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new ProductsPageResponse(page.map(ProductResponse::fromProduct).toList(), pageInfo);
	}

	@GetMapping("/popular")
	public PopularProductsPageResponse getPopularProducts(@Valid @ModelAttribute
	PopularProductPageRequest paramDto) {
		Page<PopularProduct> page = popularProductService.findPopularWithPage(paramDto);

		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new PopularProductsPageResponse(
			page.map(PopularProductResponse::fromPopularProduct).toList(),
			pageInfo);
	}
}
