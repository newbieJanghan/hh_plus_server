package my.ecommerce.api.dto.response;

import java.util.UUID;

import lombok.Getter;
import my.ecommerce.domain.product.Product;

@Getter
public class PopularProductResponse extends ProductResponse {
	private final long soldAmountInPeriod;

	public PopularProductResponse(UUID id, String name, long price, long stock, long soldAmountInPeriod) {
		super(id, name, price, stock);
		this.soldAmountInPeriod = soldAmountInPeriod;
	}

	public static PopularProductResponse fromPopularProduct(Product popularProduct) {
		return new PopularProductResponse(popularProduct.getId(), popularProduct.getName(),
			popularProduct.getPrice(), popularProduct.getStock(), popularProduct.getSoldAmountInPeriod());
	}
}
