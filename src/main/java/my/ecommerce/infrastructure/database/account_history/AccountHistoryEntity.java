package my.ecommerce.infrastructure.database.account_history;

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
import my.ecommerce.infrastructure.database.account.AccountEntity;
import my.ecommerce.infrastructure.database.common.BaseEntity;

@Entity
@Table(name = "account_history")
@Getter
@NoArgsConstructor
public class AccountHistoryEntity extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private AccountEntity userAccount;

	@Column(nullable = false)
	private long amount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@Builder
	public AccountHistoryEntity(UUID id, AccountEntity userAccount, long amount, TransactionType type) {
		super(id);
		this.userAccount = userAccount;
		this.amount = amount;
		this.type = type;
	}

	public enum TransactionType {
		DEPOSIT,
		WITHDRAW
	}
}
