package my.ecommerce.batch.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import my.ecommerce.infrastructure.database.order.OrderEntity;
import my.ecommerce.utils.UUIDGenerator;

@Component
public class OrderBulkInsertRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public OrderBulkInsertRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private List<OrderEntity> createEntities(int batchSize) {
		List<OrderEntity> list = new ArrayList<>();
		for (int i = 0; i < batchSize; i++) {
			OrderEntity entity = OrderEntity.builder()
				.id(UUIDGenerator.generate())
				.userId(UUIDGenerator.generate())
				.totalPrice(1000)
				.build();
			list.add(entity);
		}

		return list;
	}

	public List<OrderEntity> bulkInsert(int batchSize) {
		List<OrderEntity> entities = createEntities(batchSize);

		String sql = "INSERT INTO orders (id, created_at, updated_at, user_id, total_price) VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderEntity entity = entities.get(i);

				ps.setObject(1, entity.getId());
				ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
				ps.setObject(4, entity.getUserId());
				ps.setLong(5, entity.getTotalPrice());
			}

			@Override
			public int getBatchSize() {
				return batchSize;
			}
		});

		return entities;
	}
}
