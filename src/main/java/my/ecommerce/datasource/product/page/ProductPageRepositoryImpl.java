package my.ecommerce.datasource.product.page;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.product.JpaProductRepository;
import my.ecommerce.datasource.product.ProductConverter;
import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.page.ProductPageCursorQuery;
import my.ecommerce.domain.product.page.ProductPageRepository;
import my.ecommerce.domain.product.popular.PeriodQuery;
import my.ecommerce.domain.product.popular.PopularProduct;

@Repository
public class ProductPageRepositoryImpl implements ProductPageRepository {
	private JpaProductRepository jpaRepository;
	private ProductConverter domainConverter = new ProductConverter();

	@Autowired
	public ProductPageRepositoryImpl(JpaProductRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public Page<Product> findAllWithPage(ProductPageCursorQuery query) {
		UUID cursor = query.getCursor();
		PageRequest pageRequest = makePageRequest(query);
		if (cursor == null) {
			return jpaRepository.findAll(pageRequest).map(domainConverter::toDomain);
		}

		return jpaRepository.findAllNextPage(cursor, pageRequest).map(domainConverter::toDomain);
	}

	public Page<PopularProduct> findAllPopularWithPage(ProductPageCursorQuery query,
		PeriodQuery period) {
		return null;
	}

	private PageRequest makePageRequest(ProductPageCursorQuery query) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		return PageRequest.of(0, query.getLimit(), sort);
	}
}
