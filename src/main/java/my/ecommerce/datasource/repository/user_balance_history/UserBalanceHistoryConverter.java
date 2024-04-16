package my.ecommerce.datasource.repository.user_balance_history;

import my.ecommerce.datasource.entity.UserBalanceHistoryEntity;
import my.ecommerce.datasource.repository.DomainConverter;
import my.ecommerce.datasource.repository.user_balance.UserBalanceConverter;
import my.ecommerce.domain.balance.balance_history.UserBalanceHistory;

public class UserBalanceHistoryConverter extends DomainConverter<UserBalanceHistory, UserBalanceHistoryEntity> {
	private UserBalanceConverter userBalanceConverter = new UserBalanceConverter();

	public UserBalanceHistoryEntity toEntity(UserBalanceHistory domain) {
		return UserBalanceHistoryEntity.builder()
			.id(domain.getId())
			.userBalance(userBalanceConverter.toEntity(domain.getBalance()))
			.amount(domain.getAmount())
			.type(toEntityTransactionType(domain.getType()))
			.build();
	}

	public UserBalanceHistory toDomain(UserBalanceHistoryEntity entity) {
		return UserBalanceHistory.builder()
			.id(entity.getId())
			// .balance(userBalanceConverter.toDomain(entity.getUserBalance()))
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
