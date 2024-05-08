package my.ecommerce.infrastructure.database.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountRepository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
	private final JpaAccountRepository jpaRepository;

	@Autowired
	public AccountRepositoryImpl(JpaAccountRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public Account save(Account domain) {
		AccountEntity entity = AccountEntity.fromDomain(domain);
		jpaRepository.save(entity);
		return entity.toDomain();
	}

	public Account findByUserId(UUID userId) {
		return jpaRepository.findByUserId(userId).map(AccountEntity::toDomain).orElse(null);
	}

	public Account findByIdForUpdate(UUID userId) {
		return jpaRepository
			.findWithPessimisticLockById(userId)
			.map(AccountEntity::toDomain)
			.orElse(null);
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
