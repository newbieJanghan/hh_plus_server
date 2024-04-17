package my.ecommerce.datasource.user_balance;

import my.ecommerce.domain.balance.UserBalance;

public class UserBalanceConverter {
	public UserBalanceEntity toEntity(UserBalance domain) {
		return UserBalanceEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.amount(domain.getAmount())
			.build();
	}

	public UserBalance toDomain(UserBalanceEntity entity) {
		return UserBalance.builder()
			.id(entity.getId())
			.userId(entity.getUserId())
			.amount(entity.getAmount())
			.build();
	}
}
