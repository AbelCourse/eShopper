package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
