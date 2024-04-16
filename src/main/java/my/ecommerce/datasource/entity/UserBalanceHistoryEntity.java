package my.ecommerce.datasource.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_balance_history")
@Getter
@NoArgsConstructor
public class UserBalanceHistoryEntity extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_balance_id", nullable = false)
	private UserBalanceEntity userBalance;

	@Column(nullable = false)
	private long amount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	public UserBalanceHistoryEntity(UserBalanceEntity userBalance, long amount, TransactionType type) {
		this.userBalance = userBalance;
		this.amount = amount;
		this.type = type;
	}

	private enum TransactionType {
		DEPOSIT,
		WITHDRAW
	}
}
