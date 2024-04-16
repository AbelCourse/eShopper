package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
