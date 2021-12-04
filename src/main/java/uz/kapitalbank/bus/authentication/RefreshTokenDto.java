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
public class RefreshTokenDto {
    @NotBlank(message = "refresh token cannot be empty")
    private String refreshToken;
}
