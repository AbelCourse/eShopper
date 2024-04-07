package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
