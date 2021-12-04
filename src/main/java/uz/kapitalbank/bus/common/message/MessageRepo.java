package uz.kapitalbank.bus.common.message;

/**
 * @author Rustam Khalmatov
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.kapitalbank.bus.common.models.Lang;

import java.util.Optional;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
//    @Cacheable(value = CacheNames.MESSAGES, key = "#key+ '-' + #lang")
    Optional<Message> findTopByKeyAndLang(String key, Lang lang);

    Optional<Message> findByKeyAndLang(String key, Lang lang);
}
