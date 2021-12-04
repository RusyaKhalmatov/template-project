package uz.kapitalbank.bus.common.models;

/**
 * @author Rustam Khalmatov
 */

public class ResponseMessage {
    private String message;

    public ResponseMessage() {
        this.message = "";
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseMessage message(final String message) {
        return new ResponseMessage(message);
    }
}
