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
import my.ecommerce.domain.product.page.ProductPageCursorQuery;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	private JpaProductRepository jpaRepository;
	private ProductConverter domainConverter = new ProductConverter();

	@Autowired
	public ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
		this.jpaRepository = jpaProductRepository;
	}

	public Product findById(UUID id) {
		return jpaRepository.findById(id).map(domainConverter::toDomain).orElse(null);
	}

	public Page<Product> findAllWithPage(ProductPageCursorQuery query) {
		PageRequest pageRequest = makePageRequest(query);
		if (query.getCursor() == null) {
			return jpaRepository.findAll(pageRequest).map(domainConverter::toDomain);
		}

		return jpaRepository.findAllNextPage(query.getCursor(), pageRequest).map(domainConverter::toDomain);
	}

	public List<Product> findAll() {
		return jpaRepository.findAll(Sort.by(DESC, "createdAt")).stream().map(domainConverter::toDomain).toList();
	}

	public Product save(Product domain) {
		ProductEntity entity = domainConverter.toEntity(domain);
		jpaRepository.save(entity);

		domain.persist(entity.getId());
		return domain;
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}

	private PageRequest makePageRequest(ProductPageCursorQuery query) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		return PageRequest.of(0, query.getLimit(), sort);
	}
}
