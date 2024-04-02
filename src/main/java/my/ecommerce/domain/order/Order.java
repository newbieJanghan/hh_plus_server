package my.ecommerce.domain.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {
    private long id;

    @Builder
    private Order(long id) {
        this.id = id;
    }
}
