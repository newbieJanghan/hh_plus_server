package my.ecommerce.infrastructure.database.product;

import static org.springframework.data.domain.Sort.Direction.*;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;
import my.ecommerce.domain.product.dto.PeriodQuery;
import my.ecommerce.domain.product.dto.ProductPageCursorQuery;
import my.ecommerce.infrastructure.database.product.custom.PopularProductConverter;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	private final JpaProductRepository jpaRepository;
	private final PopularProductConverter popularProductConverter = new PopularProductConverter();

	@Autowired
	public ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
		this.jpaRepository = jpaProductRepository;
	}

	public Product findById(UUID id) {
		return jpaRepository.findById(id).map(ProductEntity::toDomain).orElse(null);
	}

	public Page<Product> findAllWithPage(ProductPageCursorQuery query) {
		PageRequest pageRequest = makePageRequest(query);
		if (query.getCursor() == null) {
			return jpaRepository.findAll(pageRequest).map(ProductEntity::toDomain);
		}

		return jpaRepository.findAllWithPage(query.getCursor(), pageRequest).map(ProductEntity::toDomain);
	}

	public Page<Product> findAllPopularWithPage(ProductPageCursorQuery query, PeriodQuery period) {
		PageRequest pageRequest = makePopularPageRequest(query);
		return jpaRepository.findAllPopularWithPage(period.getFrom(), period.getTo(), pageRequest)
			.map(popularProductConverter::toDomain);
	}

	public List<Product> findAll() {
		return jpaRepository.findAll(Sort.by(DESC, "createdAt")).stream().map(ProductEntity::toDomain).toList();
	}

	public Product save(Product domain) {
		ProductEntity entity = ProductEntity.fromDomain(domain);
		jpaRepository.save(entity);

		return entity.toDomain();
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}

	private PageRequest makePageRequest(ProductPageCursorQuery query) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		return PageRequest.of(0, query.getLimit(), sort);
	}

	private PageRequest makePopularPageRequest(ProductPageCursorQuery query) {
		Sort sort = Sort.by(Sort.Direction.DESC, "soldAmountInPeriod");
		int limit = Math.max(30, query.getLimit()); // TODO jpa repository 에서 limit 이 동작하지 않음.
		return PageRequest.of(0, 5, sort);
	}
}
