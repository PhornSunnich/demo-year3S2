package nichy.demo.Repository;

import nichy.demo.Entity.User; // Make sure to import your User entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used in your service for loadUserByUsername
    Optional<User> findByEmail(String email); // Change to findByUsername(String username) if using username

    // Used in your service for registerUser
    boolean existsByEmail(String email);

}