package my.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.batch.repository.OrderBulkInsertRepository;
import my.batch.repository.OrderItemBulkInsertRepository;
import my.batch.repository.ProductBulkInsertRepository;
import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.infrastructure.database.order_item.OrderItemEntity;
import my.ecommerce.infrastructure.database.product.ProductEntity;

@Component
public class BulkInsertOrderItemService {
	private final ProductBulkInsertRepository productBulkInsertRepository;
	private final OrderBulkInsertRepository orderBulkInsertRepository;
	private final OrderItemBulkInsertRepository orderItemBulkInsertRepository;

	@Autowired
	public BulkInsertOrderItemService(ProductBulkInsertRepository productBulkInsertRepository,
		OrderBulkInsertRepository orderBulkInsertRepository,
		OrderItemBulkInsertRepository orderItemBulkInsertRepository) {
		this.productBulkInsertRepository = productBulkInsertRepository;
		this.orderBulkInsertRepository = orderBulkInsertRepository;
		this.orderItemBulkInsertRepository = orderItemBulkInsertRepository;
	}

	public void execute(int productSize, int orderSize, int orderItemSize) {
		List<ProductEntity> productEntities = productBulkInsertRepository.bulkInsert(productSize);
		System.out.println("Product entities: " + productEntities.size());

		List<OrderEntity> orderEntities = orderBulkInsertRepository.bulkInsert(orderSize);
		System.out.println("Order entities: " + orderEntities.size());

		List<OrderItemEntity> orderItemEntities = orderItemBulkInsertRepository.bulkInsert(orderItemSize,
			productEntities, orderEntities);
		System.out.println("Order item entities: " + orderItemEntities.size());
	}
}
