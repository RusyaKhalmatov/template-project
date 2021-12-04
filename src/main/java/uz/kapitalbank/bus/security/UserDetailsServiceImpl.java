package uz.kapitalbank.bus.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.kapitalbank.bus.user.User;
import uz.kapitalbank.bus.user.UserService;

/**
 * @author Rustam Khalmatov
 */

@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userService.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user not found exist"));
        return SecurityUser.fromUser(user);
    }


}
