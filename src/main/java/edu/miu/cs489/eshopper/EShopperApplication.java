package edu.miu.cs489.eshopper;

import edu.miu.cs489.eshopper.model.Role;
import edu.miu.cs489.eshopper.model.User;
import edu.miu.cs489.eshopper.repository.RoleRepository;
import edu.miu.cs489.eshopper.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication

public class EShopperApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public EShopperApplication(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(EShopperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var user = userRepository.findByEmail("admin");
        Set<Role> roles = new HashSet<>();
        if (user.isEmpty()) {

            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null || adminRole.getName().isEmpty()) {
                var newAdminRole = new Role(null, "ROLE_ADMIN");
                roles.add(newAdminRole);
            } else {
                roles.add(adminRole);
            }
//            User user1 = new User(null, "admin", "admin",
//                    passwordEncoder.encode("admin"), "admin@admin.com",
//                    true, true, true, true);
            User user1 = new User(null, "firstName", "lastName",
                    passwordEncoder.encode("admin"), "admin@admin.com");
            user1.setRoles(roles);
//            roleRepository.saveAll(roles);


            System.err.println("Creating admin user -> " +
                    userRepository.save(user1));


        }


    }


}
