package my.ecommerce.domain.product.popular;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PopularProductsPeriodQueryDto {
	LocalDateTime from;
	LocalDateTime to;

	@Builder
	private PopularProductsPeriodQueryDto(LocalDateTime from, LocalDateTime to) {
		this.from = from;
		this.to = to;
	}
}
