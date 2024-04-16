package my.ecommerce.datasource.repository.user_balance_history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.entity.UserBalanceHistoryEntity;
import my.ecommerce.datasource.repository.AbstractCRUDRepository;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistoryRepository;

@Repository
public class UserBalanceHistoryRepositoryImpl
	extends AbstractCRUDRepository<UserBalanceHistory, UserBalanceHistoryEntity>
	implements UserBalanceHistoryRepository {

	@Autowired
	public UserBalanceHistoryRepositoryImpl(JpaUserBalanceHistoryRepository jpaRepository) {
		super(jpaRepository, new UserBalanceHistoryConverter());
		this.jpaRepository = jpaRepository;
	}
}
