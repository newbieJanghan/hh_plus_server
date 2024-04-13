package my.ecommerce.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.dto.CursorPagedProductsQueryDto;
import my.ecommerce.domain.product.dto.PopularProductsPeriodQueryDto;

@Repository
public interface ProductPageRepository {
	Page<Product> findAllWithPage(CursorPagedProductsQueryDto paramsDto);

	Page<PopularProduct> findAllPopularWithPage(CursorPagedProductsQueryDto paramDto,
		PopularProductsPeriodQueryDto periodDto);
}
