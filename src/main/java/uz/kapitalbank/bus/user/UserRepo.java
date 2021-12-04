package uz.kapitalbank.bus.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Rustam Khalmatov
 */


public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u from User u where u.username=:username and u.userState<>:userState")
    Optional<User> findByUsernameAndUserStateIsNot(String username, UserState userState);
}
