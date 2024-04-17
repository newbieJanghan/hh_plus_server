package my.ecommerce.datasource.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
}
