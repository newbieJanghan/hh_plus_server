package my.ecommerce.datasource.repository.user_balance;

import my.ecommerce.datasource.entity.UserBalanceEntity;
import my.ecommerce.datasource.repository.DomainConverter;
import my.ecommerce.domain.balance.UserBalance;

public class UserBalanceConverter extends DomainConverter<UserBalance, UserBalanceEntity> {
	public UserBalanceEntity toEntity(UserBalance domain) {
		return new UserBalanceEntity(domain.getId(), domain.getUserId(), domain.getAmount(), null);
	}

	public UserBalance toDomain(UserBalanceEntity entity) {
		return UserBalance.builder().id(entity.getId()).userId(entity.getUserId()).amount(entity.getAmount()).build();
	}
}
