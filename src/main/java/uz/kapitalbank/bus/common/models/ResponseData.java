package uz.kapitalbank.bus.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Rustam Khalmatov
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    @JsonProperty("data")
    private T data;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("timestamp")
    private long timestamp;

    public ResponseData(T data) {
        this.data = data;
        this.errorMessage = "";
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseData(String successMessage) {
        this.errorMessage = "";
        this.data = (T) successMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseData(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }


    public ResponseData() {
        this.errorMessage = "";
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseEntity<ResponseData<T>> response(T data) {
        return ResponseEntity.ok(new ResponseData<>(data));
    }

    public static <T> ResponseEntity<ResponseData<T>> response(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseData<>(data), httpStatus);
    }

    public static <T> ResponseEntity<ResponseData<T>> response(ResponseData<T> responseData, HttpStatus status) {
        return new ResponseEntity<>(responseData, status);
    }

    public static <T> ResponseEntity<ResponseData<T>> response(String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseData<>(null, errorMessage), httpStatus);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

//    public ResponseData<?> addEmptyListToResponse() {
//        return addListToResponse(List.of(getData()));
//    }
//
//    public <G> ResponseData<List<G>> addListToResponse(List<G> list) {
//        return new ResponseData<>(list, getErrorMessage());
//    }
}
