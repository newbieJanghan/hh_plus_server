package my.ecommerce.infrastructure.database.account_history;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.domain.account.account_history.AccountHistoryRepository;
import my.ecommerce.infrastructure.database.account.AccountConverter;
import my.ecommerce.infrastructure.database.account.AccountEntity;

@Repository
public class AccountHistoryRepositoryImpl implements AccountHistoryRepository {
	private final JpaAccountHistoryRepository jpaRepository;
	private final AccountHistoryConverter historyConverter = new AccountHistoryConverter();
	private final AccountConverter accountConverter = new AccountConverter();

	@Autowired
	public AccountHistoryRepositoryImpl(JpaAccountHistoryRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public AccountHistory save(AccountHistory history) {
		AccountEntity accountEntity = accountConverter.toEntity(history.getAccount());
		AccountHistoryEntity entity = historyConverter.toEntity(history, accountEntity);
		jpaRepository.save(entity);

		history.persist(entity.getId());
		return history;
	}

	public void destroy(UUID id) {
		this.jpaRepository.deleteById(id);
	}
}
