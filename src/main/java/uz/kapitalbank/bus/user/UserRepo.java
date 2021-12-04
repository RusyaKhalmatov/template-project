package uz.kapitalbank.bus.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Rustam Khalmatov
 */


public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
