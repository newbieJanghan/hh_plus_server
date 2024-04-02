package my.ecommerce.application.balance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ChargeRequestDto {
    @NotNull
    @Min(1)
    public long amount;
}
