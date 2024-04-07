package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
