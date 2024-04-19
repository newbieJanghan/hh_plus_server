package my.ecommerce.api.dto.page;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.utils.UUIDGenerator;

@Getter
public abstract class CursorPageRequest implements PageRequest {
	protected final int size;
	@Nullable
	protected final Sort sort;

	@Nullable
	private final String cursor;

	public CursorPageRequest(int size, @Nullable Sort sort, @Nullable String cursor) {
		this.size = Math.max(size, 10);
		this.sort = sort;
		this.cursor = cursor;
	}

	protected UUID getUUIDCursor() {
		return cursor == null ? null : UUIDGenerator.fromString(cursor);
	}
}

