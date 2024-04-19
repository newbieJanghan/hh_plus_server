package my.ecommerce.infrastructure.database.product.popular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import my.ecommerce.infrastructure.database.product.ProductConverter;
import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.domain.product.popular.PeriodQuery;
import my.ecommerce.domain.product.popular.PopularProduct;
import my.ecommerce.domain.product.popular.PopularProductRepository;

@Repository
public class PopularProductRepositoryImpl implements PopularProductRepository {
	private JpaPopularProductRepository jpaRepository;
	private ProductConverter domainConverter = new ProductConverter();

	@Autowired
	public PopularProductRepositoryImpl(JpaPopularProductRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public Page<PopularProduct> findPopularWithPage(ProductPageCursorQuery query, PeriodQuery period) {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "soldAmountInPeriod"));
		Page<PopularProductCustom> page = jpaRepository.findAllWithPage(period.getFrom(), period.getTo(), pageRequest);

		return page.map(custom -> new PopularProduct(
			domainConverter.toDomain(custom.getProduct()),
			(int)custom.getSoldAmountInPeriod()));
	}
}
