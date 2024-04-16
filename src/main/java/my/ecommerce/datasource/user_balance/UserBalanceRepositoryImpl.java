package my.ecommerce.datasource.user_balance;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import my.ecommerce.datasource.entity.UserBalanceEntity;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.UserBalanceRepository;

@Repository
public class UserBalanceRepositoryImpl implements UserBalanceRepository {
	private JpaUserBalanceRepository jpaRepository;

	@Autowired
	public UserBalanceRepositoryImpl(JpaUserBalanceRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	public UserBalance findByUserId(UUID userId) {
		UserBalanceEntity entity = jpaRepository.findByUserId(userId);
		if (entity == null) {
			return null;
		}

		return toDomain(entity);
	}

	public UserBalance save(UserBalance userBalance) {
		UserBalanceEntity entity = fromDomain(userBalance);
		jpaRepository.save(entity);

		return toDomain(entity);
	}

	public void destroy(UUID id) {
		jpaRepository.deleteById(id);
	}

	private UserBalanceEntity fromDomain(UserBalance domain) {
		return new UserBalanceEntity(domain.getUserId(), domain.getAmount(), null);
	}

	private UserBalance toDomain(UserBalanceEntity entity) {
		return UserBalance.builder().id(entity.getId()).userId(entity.getUserId()).amount(entity.getAmount()).build();
	}
}
