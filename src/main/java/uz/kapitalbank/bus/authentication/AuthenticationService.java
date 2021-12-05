package uz.kapitalbank.bus.authentication;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.kapitalbank.bus.common.Utils;
import uz.kapitalbank.bus.common.message.MessageSingleton;
import uz.kapitalbank.bus.exceptions.JwtAuthenticationException;
import uz.kapitalbank.bus.security.JwtTokenProvider;
import uz.kapitalbank.bus.user.User;
import uz.kapitalbank.bus.user.UserService;
import uz.kapitalbank.bus.user.UserState;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static uz.kapitalbank.bus.common.models.ResponseData.response;

/**
 * @author Rustam Khalmatov
 */

@Service
public class AuthenticationService {
    private final UserService userService;
    private final MessageSingleton messageSingleton;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;



    public AuthenticationService(UserService userService,
                                 MessageSingleton messageSingleton,
                                 @Lazy BCryptPasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.messageSingleton = messageSingleton;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<?> login(LoginDto loginDto) {
        Optional<User> userOptional = userService
                .findByUsernameAndStateIsNot(Objects.requireNonNullElse(loginDto.getUsername(),""),
                        UserState.DELETED);
        if(userOptional.isEmpty())
            return messageSingleton.userNotFound();
        User user = userOptional.get();

        if(user.getUserState().equals(UserState.BLOCKED))
            return messageSingleton.userIsBlocked();

        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            return messageSingleton.incorrectCredentials();
        String newSalt = Utils.generateRandomStringLower(9);
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name(), user.getId(), newSalt);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getUsername(), newSalt);
        CompletableFuture.runAsync(()->userService.updateUserActivity(newSalt, user));
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("token", token);
        responseMap.put("refreshToken",refreshToken);

        return response(responseMap, HttpStatus.OK);
    }

    public ResponseEntity<?> refreshToken(RefreshTokenDto refreshTokenDto) {
        String newToken = "";
        Map<String, String> responseMap = new HashMap<>();
        try{
            if (jwtTokenProvider.validateToken(refreshTokenDto.getRefreshToken())){
                String userId = this.jwtTokenProvider.getUserIdFromToken(refreshTokenDto.getRefreshToken());
                Optional<User> userOptional = userService.findById(Long.parseLong(userId));
                if (userOptional.isEmpty()){
                    throw new JwtAuthenticationException("Authorization problem", HttpStatus.UNAUTHORIZED);
                }else {
                    User user = userOptional.get();
                    String newUsersSalt = Utils.generateRandomStringLower(9);
                    newToken = this.jwtTokenProvider.createToken(user.getUsername(),user.getRole().name(),user.getId(),newUsersSalt);
                    responseMap.put("token", newToken);
                    return response(responseMap,HttpStatus.OK);
                }
            }
        } catch (JwtAuthenticationException e)
        {
            return messageSingleton.unauthorized();
        }
        return messageSingleton.unauthorized();
    }
}
