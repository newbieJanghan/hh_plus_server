package my.ecommerce.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import my.ecommerce.domain.product.dto.CursorPagedPopularProductsQueryDto;

@Component
public class PopularProductApp {
	public PopularProductApp() {
	}

	public Page<PopularProduct> getPopularProductsWithPage(CursorPagedPopularProductsQueryDto paramDto) {
		return null;
	}
}
