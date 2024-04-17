package my.ecommerce.datasource.user_balance_history;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.user_balance.UserBalanceConverter;
import my.ecommerce.datasource.user_balance.UserBalanceEntity;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistoryRepository;

@Repository
public class UserBalanceHistoryRepositoryImpl implements UserBalanceHistoryRepository {
	private final JpaUserBalanceHistoryRepository jpaRepository;
	private final UserBalanceHistoryConverter historyConverter = new UserBalanceHistoryConverter();
	private final UserBalanceConverter userBalanceConverter = new UserBalanceConverter();

	@Autowired
	public UserBalanceHistoryRepositoryImpl(JpaUserBalanceHistoryRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public UserBalanceHistory save(UserBalanceHistory history) {
		UserBalanceEntity balanceEntity = userBalanceConverter.toEntity(history.getBalance());
		UserBalanceHistoryEntity entity = historyConverter.toEntity(history, balanceEntity);
		jpaRepository.save(entity);

		history.persist(entity.getId());
		return history;
	}

	public void destroy(UUID id) {
		this.jpaRepository.deleteById(id);
	}
}
