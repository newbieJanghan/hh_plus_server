package my.batch.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import my.ecommerce.infrastructure.database.product.ProductEntity;
import my.ecommerce.utils.UUIDGenerator;

@Component
public class ProductBulkInsertRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ProductBulkInsertRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private List<ProductEntity> createEntities(int batchSize) {
		List<ProductEntity> list = new ArrayList<>();
		for (int i = 0; i < batchSize; i++) {
			ProductEntity product = ProductEntity.builder()
				.id(UUIDGenerator.generate())
				.name("Product " + i)
				.price(1000)
				.stock(1000)
				.build();
			list.add(product);
		}

		return list;
	}

	public List<ProductEntity> bulkInsert(int batchSize) {
		List<ProductEntity> entities = createEntities(batchSize);

		String sql = "INSERT INTO product (id, created_at, updated_at, name, price, stock) VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ProductEntity entity = entities.get(i);

				ps.setObject(1, entity.getId());
				ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
				ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
				ps.setString(4, entity.getName());
				ps.setLong(5, entity.getPrice());
				ps.setLong(6, entity.getStock());
			}

			@Override
			public int getBatchSize() {
				return batchSize;
			}
		});

		return entities;
	}
}
