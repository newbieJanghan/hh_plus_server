package my.ecommerce.datasource.repository.user_balance_history;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import my.ecommerce.datasource.entity.UserBalanceHistoryEntity;

public interface JpaUserBalanceHistoryRepository extends JpaRepository<UserBalanceHistoryEntity, UUID> {

}
