package my.ecommerce.datasource.user_balance;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.entity.UserBalanceEntity;

@Repository
public interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, UUID> {
	UserBalanceEntity findByUserId(UUID userId);
}
