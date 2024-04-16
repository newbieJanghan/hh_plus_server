package my.ecommerce.datasource.repository.user_balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.entity.UserBalanceEntity;
import my.ecommerce.datasource.repository.AbstractCRUDRepository;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;

@Repository
public class UserBalanceRepositoryImpl extends AbstractCRUDRepository<UserBalance, UserBalanceEntity>
	implements UserBalanceRepository {
	protected JpaUserBalanceRepository jpaRepository;

	@Autowired
	public UserBalanceRepositoryImpl(JpaUserBalanceRepository jpaRepository) {
		super(jpaRepository, new UserBalanceConverter());
		this.jpaRepository = jpaRepository;
	}

	public UserBalance findByUserId(UUID userId) {
		return jpaRepository.findByUserId(userId).map(domainConverter::toDomain).orElse(null);
	}
}
