package my.ecommerce.datasource.user_balance;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.entity.UserBalanceEntity;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;

@Repository
public class UserBalanceRepositoryImpl implements UserBalanceRepository {
	private JpaUserBalanceRepository jpaRepository;

	public UserBalance findByUserId(UUID userId) {
		UserBalanceEntity entity = jpaRepository.findByUserId(userId).orElse(null);
		if (entity == null) {
			return null;
		}

		return toDomain(entity);
	}

	public UserBalance save(UserBalance userBalance) {
		// save user balance
		return null;
	}

	private UserBalance toDomain(UserBalanceEntity entity) {
		return UserBalance.builder().id(entity.getId()).userId(entity.getUserId()).amount(entity.getAmount()).build();
	}
}
