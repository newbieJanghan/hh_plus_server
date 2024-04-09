package my.ecommerce.application.api.orders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Getter
@NoArgsConstructor
public class CreateOrderRequestOrderItemDto {
    @UUID
    private java.util.UUID productId;

    @NotNull
    @Min(1)
    private long quantity;
}
