package my.ecommerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import my.ecommerce.api.dto.page.CursorPageInfo;
import my.ecommerce.api.dto.request.page.PopularProductPageRequest;
import my.ecommerce.api.dto.request.page.ProductPageRequest;
import my.ecommerce.api.dto.response.PopularProductResponse;
import my.ecommerce.api.dto.response.ProductResponse;
import my.ecommerce.api.dto.response.page.PopularProductsPageResponse;
import my.ecommerce.api.dto.response.page.ProductsPageResponse;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
	private final ProductService productService;

	@Autowired
	public ProductsController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("")
	public ProductsPageResponse getProducts(@Valid @ModelAttribute ProductPageRequest request) {
		Page<Product> page = productService.getProductPage(request.toCursorQueryDto());
		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new ProductsPageResponse(page.map(ProductResponse::fromProduct).toList(), pageInfo);
	}

	@GetMapping("/popular")
	public PopularProductsPageResponse getPopularProducts(@Valid @ModelAttribute
	PopularProductPageRequest request) {
		Page<Product> page = productService.getPopularProductPage(request.toCursorQueryDto(), request.toPeriodQuery());

		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new PopularProductsPageResponse(
			page.map(PopularProductResponse::fromPopularProduct).toList(),
			pageInfo);
	}
}
