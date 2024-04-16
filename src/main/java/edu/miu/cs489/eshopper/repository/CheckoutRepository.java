package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
}
