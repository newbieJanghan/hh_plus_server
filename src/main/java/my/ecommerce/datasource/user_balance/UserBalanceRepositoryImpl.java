package my.ecommerce.datasource.user_balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;

@Repository
public class UserBalanceRepositoryImpl implements UserBalanceRepository {
	private final JpaUserBalanceRepository jpaRepository;
	private final UserBalanceConverter domainConverter = new UserBalanceConverter();

	@Autowired
	public UserBalanceRepositoryImpl(JpaUserBalanceRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public UserBalance save(UserBalance domain) {
		UserBalanceEntity entity = domainConverter.toEntity(domain);
		jpaRepository.save(entity);

		domain.persist(entity.getId());
		return domain;
	}

	public UserBalance findByUserId(UUID userId) {
		return jpaRepository.findByUserId(userId).map(domainConverter::toDomain).orElse(null);
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}
}
