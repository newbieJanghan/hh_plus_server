package my.ecommerce.infrastructure.database.account;

import my.ecommerce.domain.account.Account;

public class AccountConverter {
	public AccountEntity toEntity(Account domain) {
		return AccountEntity.builder()
			.id(domain.getId())
			.userId(domain.getUserId())
			.amount(domain.getAmount())
			.build();
	}

	public Account toDomain(AccountEntity entity) {
		return Account.builder()
			.id(entity.getId())
			.userId(entity.getUserId())
			.amount(entity.getAmount())
			.build();
	}
}
