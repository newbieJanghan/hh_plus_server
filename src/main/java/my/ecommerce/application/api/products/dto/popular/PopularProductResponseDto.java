package my.ecommerce.application.api.products.dto.popular;

import lombok.Getter;
import my.ecommerce.application.api.products.dto.ProductResponseDto;
import my.ecommerce.domain.product.PopularProduct;

import java.util.UUID;

@Getter
public class PopularProductResponseDto extends ProductResponseDto {
    private final long soldAmountInPeriod;

    public PopularProductResponseDto(UUID id, String name, long price, long stock, long soldAmountInPeriod) {
        super(id, name, price, stock);
        this.soldAmountInPeriod = soldAmountInPeriod;
    }

    public static PopularProductResponseDto fromPopularProduct(PopularProduct popularProduct) {
        return new PopularProductResponseDto(popularProduct.getId(), popularProduct.getName(), popularProduct.getPrice(), popularProduct.getStock(), popularProduct.getSoldAmountInPeriod());
    }
}
