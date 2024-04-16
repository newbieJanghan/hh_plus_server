package my.ecommerce.datasource.repository.user_balance;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import my.ecommerce.datasource.entity.UserBalanceEntity;

public interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, UUID> {
	Optional<UserBalanceEntity> findByUserId(UUID userId);
}
