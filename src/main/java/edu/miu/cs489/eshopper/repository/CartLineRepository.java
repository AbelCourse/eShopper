package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.CartLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartLineRepository extends JpaRepository<CartLine, Long> {
}
