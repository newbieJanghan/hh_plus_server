package my.ecommerce.infrastructure.database.account_history;

import my.ecommerce.infrastructure.database.account.UserBalanceEntity;
import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.account.account_history.AccountHistory;

public class UserBalanceHistoryConverter {
	public UserBalanceHistoryEntity toEntity(AccountHistory domain, UserBalanceEntity userBalance) {
		return UserBalanceHistoryEntity.builder()
			.id(domain.getId())
			.userBalance(userBalance)
			.amount(domain.getAmount())
			.type(toEntityTransactionType(domain.getType()))
			.build();
	}

	public AccountHistory toDomain(UserBalanceHistoryEntity entity) {
		return AccountHistory.builder()
			.id(entity.getId())
			.amount(entity.getAmount())
			.type(toDomainTransactionType(entity.getType()))
			.build();
	}

	public AccountHistory toDomain(UserBalanceHistoryEntity entity, Account account) {
		return AccountHistory.builder()
			.id(entity.getId())
			.balance(account)
			.amount(entity.getAmount())
			.type(toDomainTransactionType(entity.getType()))
			.build();
	}

	private UserBalanceHistoryEntity.TransactionType toEntityTransactionType(
		AccountHistory.BalanceHistoryType type) {
		return type == AccountHistory.BalanceHistoryType.CHARGE
			? UserBalanceHistoryEntity.TransactionType.DEPOSIT
			: UserBalanceHistoryEntity.TransactionType.WITHDRAW;
	}

	private AccountHistory.BalanceHistoryType toDomainTransactionType(
		UserBalanceHistoryEntity.TransactionType type) {
		return type == UserBalanceHistoryEntity.TransactionType.DEPOSIT
			? AccountHistory.BalanceHistoryType.CHARGE
			: AccountHistory.BalanceHistoryType.USAGE;
	}
}
