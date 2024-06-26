package my.ecommerce.infrastructure.database.order_item;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
}
