package uz.kapitalbank.bus.exceptions;

import org.springframework.http.ResponseEntity;
import uz.kapitalbank.bus.common.models.ResponseData;
import uz.kapitalbank.bus.common.models.ResponseMessage;

import java.util.Objects;

/**
 * @author Rustam Khalmatov
 */


public class CommonException extends RuntimeException {
    private ResponseEntity<?> responseEntity;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(ResponseEntity<?> responseEntity) {
        super(Objects.requireNonNull((ResponseData) responseEntity.getBody()).getErrorMessage());
        this.responseEntity = responseEntity;
    }

    public ResponseEntity<?> getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity<ResponseData<ResponseMessage>> responseEntity) {
        this.responseEntity = responseEntity;
    }
}
