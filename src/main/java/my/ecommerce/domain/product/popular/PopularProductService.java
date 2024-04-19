package my.ecommerce.domain.product.popular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import my.ecommerce.api.dto.request.page.PopularProductPageRequest;

@Service
public class PopularProductService {
	private final PopularProductRepository popularProductRepository;

	@Autowired
	public PopularProductService(PopularProductRepository popularProductRepository) {
		this.popularProductRepository = popularProductRepository;
	}

	public Page<PopularProduct> findPopularWithPage(PopularProductPageRequest request) {
		return popularProductRepository.findPopularWithPage(request.toCursorQueryDto(),
			request.toPopularPeriodQueryDto());
	}
}
