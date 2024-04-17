package my.ecommerce.datasource.user_balance_history;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserBalanceHistoryRepository extends JpaRepository<UserBalanceHistoryEntity, UUID> {

}
