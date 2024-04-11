package my.ecommerce.application.page;

import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

interface CursorByIdEntity {
    UUID getId();
}

public class CursorPage<T extends CursorByIdEntity> extends PageImpl<T> {
    public CursorPage(List<T> content) {
        super(content);
    }

    public UUID getCursor() {
        return this.getContent().getLast().getId();
    }
}
