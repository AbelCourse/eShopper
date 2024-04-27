package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    //void saveUser(String email, String password, Set<Role> roles);

//    Optional<User> save(User user);
}
