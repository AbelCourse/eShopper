package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.email = ?1 AND o.orderId = ?2")
    Order findOrderByEmailAndOrderId(String email, Long cartId);

    @Query("SELECT o FROM Order o WHERE o.email = ?1")
    List<Order> findAllByEmail(String emailId);

}
