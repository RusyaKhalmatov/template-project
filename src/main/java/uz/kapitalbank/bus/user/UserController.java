package uz.kapitalbank.bus.user;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.kapitalbank.bus.user.models.UserCreateDto;

/**
 * @author Rustam Khalmatov
 */

@Api(value = "User controller")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("create/user")
    @PreAuthorize("hasAuthority('user:admin-user')")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto userDto){
        return userService.createUser(userDto);
    }

    @PostMapping("create/admin")
    @PreAuthorize("hasAuthority('user:super-admin-user')")
    public ResponseEntity<?> createAdminUser(@RequestBody UserCreateDto userDto){
        return userService.createAdminUser(userDto);
    }

    @GetMapping("get/{userId}")
    @PreAuthorize("hasAuthority('user:super-admin-user')")
    public ResponseEntity<?> getById(@PathVariable("userId") Long userId){
        return userService.getUser(userId);
    }


}
