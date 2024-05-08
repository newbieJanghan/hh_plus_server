package my.ecommerce.infrastructure.database.account_history;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.domain.account.account_history.AccountHistory;
import my.ecommerce.infrastructure.database.account.AccountEntity;
import my.ecommerce.infrastructure.database.common.BaseEntity;

@Entity
@Table(name = "account_history")
@Getter
@NoArgsConstructor
public class AccountHistoryEntity extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private AccountEntity account;

	@Column(nullable = false)
	private long amount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@Builder
	public AccountHistoryEntity(UUID id, AccountEntity account, long amount, TransactionType type) {
		super(id);
		this.account = account;
		this.amount = amount;
		this.type = type;
	}

	public static AccountHistoryEntity fromDomain(AccountHistory domain) {
		return AccountHistoryEntity.builder()
			.id(domain.getId())
			.account(AccountEntity.fromDomain(domain.getAccount()))
			.amount(domain.getAmount())
			.type(fromDomainType(domain.getType()))
			.build();
	}

	public static List<AccountHistoryEntity> fromDomain(List<AccountHistory> histories) {
		if (histories == null) {
			return List.of();
		}
		return histories.stream()
			.map(AccountHistoryEntity::fromDomain)
			.toList();
	}

	public static TransactionType fromDomainType(AccountHistory.TransactionType type) {
		return switch (type) {
			case CHARGE -> TransactionType.DEPOSIT;
			case USAGE -> TransactionType.WITHDRAW;
		};
	}

	public AccountHistory toDomain() {
		return AccountHistory.builder()
			.id(getId())
			.amount(amount)
			.type(toDomainType())
			.build();
	}

	private AccountHistory.TransactionType toDomainType() {
		return switch (type) {
			case DEPOSIT -> AccountHistory.TransactionType.CHARGE;
			case WITHDRAW -> AccountHistory.TransactionType.USAGE;
		};
	}

	public enum TransactionType {
		DEPOSIT,
		WITHDRAW
	}
}
