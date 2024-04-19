package my.ecommerce.datasource.user_balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.AccountRepository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
	private final JpaUserBalanceRepository jpaRepository;
	private final UserBalanceConverter domainConverter = new UserBalanceConverter();

	@Autowired
	public AccountRepositoryImpl(JpaUserBalanceRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public Account save(Account domain) {
		UserBalanceEntity entity = domainConverter.toEntity(domain);
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
