package my.ecommerce.datasource.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.AllArgsConstructor;
import my.ecommerce.datasource.entity.BaseEntity;
import my.ecommerce.domain.BaseDomain;

@AllArgsConstructor
public abstract class AbstractCRUDRepository<D extends BaseDomain, E extends BaseEntity> {
	protected JpaRepository<E, UUID> jpaRepository;
	protected DomainConverter<D, E> domainConverter;

	public D save(D domain) {
		E entity = jpaRepository.save(domainConverter.toEntity(domain));
		domain.persist(entity.getId());
		return domain;
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}

	public D findOneById(UUID id) {
		return jpaRepository.findById(id).map(domainConverter::toDomain).orElse(null);
	}

}
