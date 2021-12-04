package uz.kapitalbank.bus.user.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Rustam Khalmatov
 */

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String orgName;

}
