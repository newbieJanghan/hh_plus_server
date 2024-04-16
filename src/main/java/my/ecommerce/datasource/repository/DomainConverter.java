package my.ecommerce.datasource.repository;

public abstract class DomainConverter<D, E> {
	public abstract E toEntity(D domain);

	public abstract D toDomain(E entity);
}
