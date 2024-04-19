package my.ecommerce.api.dto.page;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Sort {
	private final String field;
	private final Direction direction;

	@Builder
	public Sort(String field, Direction direction) {
		this.field = field;
		this.direction = direction;
	}

	public enum Direction {
		ASC, DESC
	}
}
