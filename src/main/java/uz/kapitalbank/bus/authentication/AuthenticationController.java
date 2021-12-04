package uz.kapitalbank.bus.authentication;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rustam Khalmatov
 */


@RestController
@RequestMapping("api/v1/auth")
@Api(value = "Authentication controller")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

}
