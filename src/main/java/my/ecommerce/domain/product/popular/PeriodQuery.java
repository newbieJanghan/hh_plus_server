package my.ecommerce.domain.product.popular;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PeriodQuery {
	LocalDateTime from;
	LocalDateTime to;

	@Builder
	public PeriodQuery(LocalDateTime from, LocalDateTime to) {
		this.from = from;
		this.to = to;
	}
}
