package my.ecommerce.application.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateOrderRequestDto {
    @Valid
    @NotNull
    private List<CreateOrderRequestOrderItemDto> items;

    @NotNull
    @Min(0)
    private long totalPrice;
}
