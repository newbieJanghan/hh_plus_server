package my.ecommerce.infrastructure.database.account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, UUID> {
	Optional<AccountEntity> findByUserId(UUID userId);

	@Query(value = "SELECT * FROM account a WHERE a.id = :id FOR UPDATE", nativeQuery = true)
	Optional<AccountEntity> findWithPessimisticLockById(UUID id);
}
