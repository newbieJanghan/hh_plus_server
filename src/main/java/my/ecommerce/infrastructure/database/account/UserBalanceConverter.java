package my.ecommerce.infrastructure.database.account;

import my.ecommerce.domain.account.Account;

public class UserBalanceConverter {
	public UserBalanceEntity toEntity(Account domain) {
		return UserBalanceEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.amount(domain.getAmount())
			.build();
	}

	public Account toDomain(UserBalanceEntity entity) {
		return Account.builder()
			.id(entity.getId())
			.userId(entity.getUserId())
			.amount(entity.getAmount())
			.build();
	}
}
