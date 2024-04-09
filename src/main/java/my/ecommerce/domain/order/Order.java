package my.ecommerce.domain.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class Order {
    private UUID id;

    @Builder
    private Order(UUID id) {
        this.id = id;
    }
}
