package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
