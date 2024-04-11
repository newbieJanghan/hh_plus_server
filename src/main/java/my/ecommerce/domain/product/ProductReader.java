package my.ecommerce.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.domain.product.dto.PopularProductsPeriodQueryDto;

@Component
public class ProductReader {
	public ProductReader() {
	}

	public Page<PopularProduct> getPopularProductsWithPage(CursorPagedProductsQueryDto paramDto,
		PopularProductsPeriodQueryDto periodDto) {
		return null;
	}

	public Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramDto) {
		return null;
	}
}
