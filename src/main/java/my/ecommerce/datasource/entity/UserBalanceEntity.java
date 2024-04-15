package my.ecommerce.datasource.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_balance")
@Getter
@NoArgsConstructor
public class UserBalanceEntity extends BaseEntity {

	@Column(nullable = false, name = "user_id")
	private UUID userId;

	@Column(columnDefinition = "LONG DEFAULT 0")
	private long amount;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userBalance")
	@JoinColumn(referencedColumnName = "user_balance_id")
	private List<UserBalanceHistoryEntity> histories;
}
