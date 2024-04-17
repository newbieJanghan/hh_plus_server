package my.ecommerce.datasource.user_balance_history;

import my.ecommerce.datasource.user_balance.UserBalanceEntity;
import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;

public class UserBalanceHistoryConverter {
	public UserBalanceHistoryEntity toEntity(UserBalanceHistory domain, UserBalanceEntity userBalance) {
		return UserBalanceHistoryEntity.builder()
			.id(domain.getId())
			.userBalance(userBalance)
			.amount(domain.getAmount())
			.type(toEntityTransactionType(domain.getType()))
			.build();
	}

	public UserBalanceHistory toDomain(UserBalanceHistoryEntity entity) {
		return UserBalanceHistory.builder()
			.id(entity.getId())
			.amount(entity.getAmount())
			.type(toDomainTransactionType(entity.getType()))
			.build();
	}

	public UserBalanceHistory toDomain(UserBalanceHistoryEntity entity, UserBalance userBalance) {
		return UserBalanceHistory.builder()
			.id(entity.getId())
			.balance(userBalance)
			.amount(entity.getAmount())
			.type(toDomainTransactionType(entity.getType()))
			.build();
	}

	private UserBalanceHistoryEntity.TransactionType toEntityTransactionType(
		UserBalanceHistory.BalanceHistoryType type) {
		return type == UserBalanceHistory.BalanceHistoryType.CHARGE
			? UserBalanceHistoryEntity.TransactionType.DEPOSIT
			: UserBalanceHistoryEntity.TransactionType.WITHDRAW;
	}

	private UserBalanceHistory.BalanceHistoryType toDomainTransactionType(
		UserBalanceHistoryEntity.TransactionType type) {
		return type == UserBalanceHistoryEntity.TransactionType.DEPOSIT
			? UserBalanceHistory.BalanceHistoryType.CHARGE
			: UserBalanceHistory.BalanceHistoryType.USAGE;
	}
}
