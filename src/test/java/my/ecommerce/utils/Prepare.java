package my.ecommerce.utils;

import java.util.UUID;

import my.ecommerce.domain.account.Account;
import my.ecommerce.domain.order.Order;
import my.ecommerce.domain.order.order_item.OrderItem;
import my.ecommerce.domain.product.Product;

public class Prepare {
	public static Order order(UUID userId, int itemsCount) {
		Order order = Order.newOrder(userId);

		for (int i = 1; i <= itemsCount; i++) {
			Product product = Prepare.product(i * 1000L, i * 1000L);
			order.addOrderItem(product, i, i * 1000L);
		}

		order.calculateCurrentPrice();
		return order;
	}

	public static OrderItem orderItem(Order order, Product product) {
		return OrderItem.newOrderItem(order, product, 1, 1000L);
	}

	public static Product product(long price, long stock) {
		return Product.builder().id(UUIDGenerator.generate()).name("test").price(price).stock(stock).build();
	}

	public static Account account(long balance) {
		return Account.builder().id(UUIDGenerator.generate()).userId(UUIDGenerator.generate()).balance(balance).build();
	}
}
