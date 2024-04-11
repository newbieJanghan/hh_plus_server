package my.ecommerce.application.page;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.utils.UUIDGenerator;

@Getter
public class CursorPageRequest implements PageRequest {
	protected final Long size;
	@Nullable
	protected final Sort sort;

	@Nullable
	private final String cursor;

	public CursorPageRequest(@Nullable Long size, @Nullable Sort sort, @Nullable String cursor) {
		this.size = Math.max(size != null ? size : 10, 10);
		this.sort = sort;
		this.cursor = cursor;
	}

	protected UUID getUUIDCursor() {
		return cursor == null ? null : UUIDGenerator.fromString(cursor);
	}
}

