package my.ecommerce.datasource.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_balance_history")
@Getter
@NoArgsConstructor
public class UserBalanceHistoryEntity extends BaseEntity {
	@Column(name = "user_balance_id")
	private UUID userBalanceId;

	@Column(nullable = false)
	private long amount;

	@Column(columnDefinition = "ENUM('DEPOSIT', 'WITHDRAW')")
	private Type type;

	private enum Type {
		DEPOSIT,
		WITHDRAW
	}
}
