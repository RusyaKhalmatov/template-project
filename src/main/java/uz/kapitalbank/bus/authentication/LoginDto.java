package uz.kapitalbank.bus.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Rustam Khalmatov
 */

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "username cannot be empty")
    private String username;
    @NotBlank(message = "password cannot be empty")
    private String password;
}
