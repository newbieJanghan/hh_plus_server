package my.ecommerce.domain;

import my.ecommerce.domain.balance.UserBalance;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.product.Product;
import my.ecommerce.utils.UUIDGenerator;

public class Prepare {
	public static Order order(int itemsCount) {
		Order order = Order.newOrder(UUIDGenerator.generate(), 0);

		for (int i = 1; i <= itemsCount; i++) {
			Product product = Product.newProduct(String.valueOf(i), 1000, 10);
			order.addOrderItem(product, i, i * 1000L);
		}

		order.calculateCurrentPrice();
		return order;
	}

	public static OrderItem orderItem(Order order, Product product) {
		return OrderItem.newOrderItem(order, product, 1, 1000L);
	}

	public static Product product(long price, long stock) {
		return Product.newProduct("Test", price, stock);
	}

	public static UserBalance userBalance() {
		return UserBalance.newBalance(UUIDGenerator.generate());
	}
}
