package my.ecommerce.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {
    private long id;

    @Builder
    private User(long id) {
        this.id = id;
    }
}
