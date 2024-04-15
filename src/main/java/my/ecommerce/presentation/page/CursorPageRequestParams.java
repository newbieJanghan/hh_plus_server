package my.ecommerce.presentation.page;

import java.util.UUID;

import org.springframework.lang.Nullable;

import lombok.Getter;
import my.ecommerce.utils.UUIDGenerator;

@Getter
public abstract class CursorPageRequestParams implements PageRequestParams {
	protected final Long size;
	@Nullable
	protected final Sort sort;

	@Nullable
	private final String cursor;

	public CursorPageRequestParams(@Nullable Long size, @Nullable Sort sort, @Nullable String cursor) {
		this.size = Math.max(size != null ? size : 10, 10);
		this.sort = sort;
		this.cursor = cursor;
	}

	protected UUID getUUIDCursor() {
		return cursor == null ? null : UUIDGenerator.fromString(cursor);
	}
}
