package my.ecommerce.infrastructure.database.account_history;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.infrastructure.database.account.AccountEntity;

public class AccountHistoryConverter {
	public AccountHistoryEntity toEntity(AccountHistory domain, AccountEntity userAccount) {
		return AccountHistoryEntity.builder()
			.id(domain.getId())
			.userAccount(userAccount)
			.amount(domain.getAmount())
			.type(toEntityTransactionType(domain.getType()))
			.build();
	}

	public AccountHistory toDomain(AccountHistoryEntity entity) {
		return AccountHistory.builder()
			.id(entity.getId())
			.amount(entity.getAmount())
			.type(toDomainTransactionType(entity.getType()))
			.build();
	}

	public AccountHistory toDomain(AccountHistoryEntity entity, Account account) {
		return AccountHistory.builder()
			.id(entity.getId())
			.account(account)
			.amount(entity.getAmount())
			.type(toDomainTransactionType(entity.getType()))
			.build();
	}

	private AccountHistoryEntity.TransactionType toEntityTransactionType(
		AccountHistory.AccountHistoryType type) {
		return type == AccountHistory.AccountHistoryType.CHARGE
			? AccountHistoryEntity.TransactionType.DEPOSIT
			: AccountHistoryEntity.TransactionType.WITHDRAW;
	}

	private AccountHistory.AccountHistoryType toDomainTransactionType(
		AccountHistoryEntity.TransactionType type) {
		return type == AccountHistoryEntity.TransactionType.DEPOSIT
			? AccountHistory.AccountHistoryType.CHARGE
			: AccountHistory.AccountHistoryType.USAGE;
	}
}
