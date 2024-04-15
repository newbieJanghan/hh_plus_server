package my.ecommerce.domain.product.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.popular.PopularProduct;
import my.ecommerce.presentation.request.page.PopularProductsPageRequestParams;
import my.ecommerce.presentation.request.page.ProductsPageRequestParams;

@Service
public class ProductPageService {
	private final ProductPageRepository productPageRepository;

	@Autowired
	public ProductPageService(ProductPageRepository productPageRepository) {
		this.productPageRepository = productPageRepository;
	}

	public Page<Product> findAllWithPage(ProductsPageRequestParams paramDto) {
		return productPageRepository.findAllWithPage(paramDto.toCursorQueryDto());
	}

	public Page<PopularProduct> findAllPopularWithPage(PopularProductsPageRequestParams paramDto) {
		return productPageRepository.findAllPopularWithPage(
			paramDto.toCursorQueryDto(),
			paramDto.toPopularPeriodQueryDto());

	}
}
