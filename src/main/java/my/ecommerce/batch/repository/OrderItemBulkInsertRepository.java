package my.ecommerce.batch.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.infrastructure.database.order_item.OrderItemEntity;
import my.ecommerce.infrastructure.database.product.ProductEntity;
import my.ecommerce.utils.UUIDGenerator;

@Component
public class OrderItemBulkInsertRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public OrderItemBulkInsertRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private List<OrderItemEntity> createEntities(int batchSize, List<ProductEntity> productEntities,
		List<OrderEntity> orderEntities) {
		List<OrderItemEntity> entities = new ArrayList<>();
		for (int i = 0; i < batchSize; i++) {
			OrderItemEntity entity = OrderItemEntity.builder()
				.id(UUIDGenerator.generate())
				.product(productEntities.get(new Random().nextInt(productEntities.size())))
				.order(orderEntities.get(new Random().nextInt(orderEntities.size())))
				.quantity(new Random().nextInt(1000))
				.currentPrice(1000)
				.build();
			entities.add(entity);
		}

		return entities;
	}

	public List<OrderItemEntity> bulkInsert(int batchSize, List<ProductEntity> productEntities,
		List<OrderEntity> orderEntities) {
		List<OrderItemEntity> entities = createEntities(batchSize, productEntities, orderEntities);

		String sql = "INSERT INTO order_item (id, created_at, updated_at, product_id, order_id, quantity, status, current_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderItemEntity entity = entities.get(i);

				ps.setObject(1, entity.getId());
				ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
				ps.setObject(4, entity.getProduct().getId());
				ps.setObject(5, entity.getOrder().getId());
				ps.setLong(6, entity.getQuantity());
				ps.setString(7, "ORDERED");
				ps.setLong(8, entity.getCurrentPrice());
			}

			@Override
			public int getBatchSize() {
				return batchSize;
			}
		});

		return entities;
	}
}
