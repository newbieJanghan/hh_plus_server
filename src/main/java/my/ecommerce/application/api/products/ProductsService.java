package my.ecommerce.application.api.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import my.ecommerce.application.api.products.dto.GetProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.application.api.products.dto.ProductsPageResponseDto;
import my.ecommerce.application.api.products.dto.popular.GetPopularProductsPageRequestParamDto;
import my.ecommerce.application.api.products.dto.popular.PopularProductResponseDto;
import my.ecommerce.application.api.products.dto.popular.PopularProductsPageResponseDto;
import my.ecommerce.application.page.CursorPageInfo;
import my.ecommerce.domain.product.PopularProduct;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductReader;

@Service
public class ProductsService {
	private final ProductReader productReader;

	@Autowired
	public ProductsService(ProductReader productReader) {
		this.productReader = productReader;
	}

	public ProductsPageResponseDto findAllWithPage(GetProductsPageRequestParamDto paramDto) {
		Page<Product> page = productReader.findAllWithPage(paramDto.toCursorQueryDto());

		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new ProductsPageResponseDto(page.map(ProductResponseDto::fromProduct).toList(), pageInfo);
	}

	public PopularProductsPageResponseDto findPopularProductsWithPage(GetPopularProductsPageRequestParamDto paramDto) {
		Page<PopularProduct> page = productReader.getPopularProductsWithPage(
			paramDto.toCursorQueryDto(),
			paramDto.toPopularPeriodQueryDto());

		String cursor = page.getContent().isEmpty() ? null : page.getContent().getLast().getId().toString();
		CursorPageInfo pageInfo = CursorPageInfo.fromPage(page, cursor);

		return new PopularProductsPageResponseDto(
			page.map(PopularProductResponseDto::fromPopularProduct).toList(),
			pageInfo);
	}
}
