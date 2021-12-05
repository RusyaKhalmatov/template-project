package uz.kapitalbank.bus.user;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kapitalbank.bus.common.message.MessageSingleton;
import uz.kapitalbank.bus.exceptions.CommonException;
import uz.kapitalbank.bus.user.models.UserCreateDto;


import javax.xml.transform.OutputKeys;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static uz.kapitalbank.bus.common.Utils.generateRandomStringLower;
import static uz.kapitalbank.bus.common.models.ResponseData.response;

/**
 * @author Rustam Khalmatov
 */

@Service
public class UserService {
    private final UserRepo userRepo;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepo userRepo,
                       MessageSingleton messageSingleton,
                       @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.messageSingleton = messageSingleton;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public ResponseEntity<?> createUser(UserCreateDto userDto) {
        try {
            User user = createUserEntity(UserRole.CLIENT, userDto);
            return response(user, HttpStatus.OK);
        }catch (CommonException ex){
            return ex.getResponseEntity();
        }
    }

    public ResponseEntity<?> createAdminUser(UserCreateDto userDto) {
        try {
            User user = createUserEntity(UserRole.ADMIN, userDto);
            return response(user, HttpStatus.OK);
        }catch (CommonException ex){
            return ex.getResponseEntity();
        }
    }

    public User createUserEntity(UserRole role, UserCreateDto userDto) throws CommonException {
        if(!userDto.getPassword().equals(userDto.getPasswordConfirm()))
            throw new CommonException(messageSingleton.passwordDontMatch());
        Optional<User> userOptional = findByUsername(userDto.getUsername());
        if(userOptional.isPresent())
            throw new CommonException(messageSingleton.userNotFound());
        User user = new User();
        user.setUserState(UserState.ACTIVE);
        user.setRole(role);
        user.setUsername(userDto.getUsername());
        user.setOrgName(userDto.getOrgName());
        user.setSalt(generateRandomStringLower(9));
        user.setDateModified(Timestamp.from(Instant.now()));
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return save(user);
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public Optional<User> findByUsernameAndStateIsNot(String username, UserState userState) {
        return userRepo.findByUsernameAndUserStateIsNot(username, userState);
    }

    public Optional<User> findById(long userId) {
        return userRepo.findById(userId);
    }

    public void updateUserActivity(String newSalt, User user) {
        user.setSalt(newSalt);
        save(user);
    }

    public ResponseEntity<?> getUser(Long userId) {
        Optional<User> userOptinal = findById(userId);
        if(userOptinal.isEmpty())
            return messageSingleton.userNotFound();
        return response(userOptinal.get(), HttpStatus.OK);
    }
}
