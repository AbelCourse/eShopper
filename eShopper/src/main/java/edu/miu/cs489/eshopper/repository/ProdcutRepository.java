package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdcutRepository extends JpaRepository<Product, Long> {
}
