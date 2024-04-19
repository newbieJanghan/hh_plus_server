package my.ecommerce.infrastructure.database.account;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountRepository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
	private final JpaAccountRepository jpaRepository;
	private final AccountConverter domainConverter = new AccountConverter();

	@Autowired
	public AccountRepositoryImpl(JpaAccountRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public Account save(Account domain) {
		AccountEntity entity = domainConverter.toEntity(domain);
		jpaRepository.save(entity);

		domain.persist(entity.getId());
		return domain;
	}

	public Account findByUserId(UUID userId) {
		return jpaRepository.findByUserId(userId).map(domainConverter::toDomain).orElse(null);
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
