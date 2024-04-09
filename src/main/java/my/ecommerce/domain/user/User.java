package my.ecommerce.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class User {
    private UUID id;

    @Builder
    private User(UUID id) {
        this.id = id;
    }
}
