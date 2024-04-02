package my.ecommerce.application.orders.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderRequestOrderItemDto {
    @NotNull
    @Min(1)
    private long productId;

    @NotNull
    @Min(1)
    private long quantity;
}
