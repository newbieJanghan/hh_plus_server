package my.ecommerce.infrastructure.database.account_history;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.domain.account.account_history.AccountHistoryRepository;

@Repository
public class AccountHistoryRepositoryImpl implements AccountHistoryRepository {
	private final JpaAccountHistoryRepository jpaRepository;

	@Autowired
	public AccountHistoryRepositoryImpl(JpaAccountHistoryRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public AccountHistory save(AccountHistory domain) {
		AccountHistoryEntity entity = AccountHistoryEntity.fromDomain(domain);
		jpaRepository.save(entity);

		return entity.toDomain();
	}

	public void destroy(UUID id) {
		this.jpaRepository.deleteById(id);
	}
}
