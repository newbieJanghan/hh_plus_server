package my.ecommerce.datasource.product;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.product.Product;
import my.ecommerce.domain.product.ProductRepository;

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

	public Product save(Product domain) {
		ProductEntity entity = domainConverter.toEntity(domain);
		jpaRepository.save(entity);

		domain.persist(entity.getId());
		return domain;
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
