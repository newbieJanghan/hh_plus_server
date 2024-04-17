package my.ecommerce.datasource.user_balance;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.ecommerce.datasource.common.BaseEntity;
import my.ecommerce.datasource.user_balance_history.UserBalanceHistoryEntity;

@Entity
@Table(name = "user_balance")
@Getter
@NoArgsConstructor
public class UserBalanceEntity extends BaseEntity {
	@Column(nullable = false, name = "user_id")
	private UUID userId;

	@Column(columnDefinition = "BIGINT DEFAULT 0")
	private long amount;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userBalance", orphanRemoval = true)
	private List<UserBalanceHistoryEntity> histories;

	@Builder
	public UserBalanceEntity(UUID id, UUID userId, long amount, List<UserBalanceHistoryEntity> histories) {
		super(id);
		this.userId = userId;
		this.amount = amount;
		this.histories = histories;
	}
}
