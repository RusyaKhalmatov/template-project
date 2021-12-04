package uz.kapitalbank.bus.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.kapitalbank.bus.common.models.ResponseData;

import static uz.kapitalbank.bus.common.models.ResponseData.response;

/**
 * @author Rustam Khalmatov
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
//        telegram.sendText(ex.getMessage());
        return responseException(ex, request, HttpStatus.BAD_REQUEST, errorMessage);
    }

    private ResponseEntity<Object> responseException(Exception ex, WebRequest request, HttpStatus status,
                                                     String message) {
//        return handleExceptionInternal(ex, message, new HttpHeaders() , status, request);

        return new ResponseEntity<>(new ResponseData<>(null,message),status);
    }
}
