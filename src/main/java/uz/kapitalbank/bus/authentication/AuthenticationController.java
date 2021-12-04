package uz.kapitalbank.bus.authentication;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Rustam Khalmatov
 */


@RestController
@RequestMapping("api/v1/auth")
@Api(value = "Authentication controller")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @PostMapping("getToken")
    @ApiOperation(value = "Get token")
    ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PostMapping("refreshToken")
    @ApiOperation(value = "Refresh token")
    ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto){
        return authService.refreshToken(refreshTokenDto);
    }

}
