package uz.kapitalbank.bus.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Rustam Khalmatov
 */


public class JwtAuthenticationException extends Exception {
    private HttpStatus httpStatus;


    public JwtAuthenticationException(String message, HttpStatus t) {
        super(message);
        this.httpStatus = t;
    }

    public JwtAuthenticationException(String s) {
        super(s);
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
