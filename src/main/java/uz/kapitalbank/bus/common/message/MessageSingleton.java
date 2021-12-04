package uz.kapitalbank.bus.common.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.kapitalbank.bus.common.models.ResponseData;
import uz.kapitalbank.bus.common.models.ResponseMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

/**
 * @author Rustam Khalmatov
 */

@Component
public class MessageSingleton {

    private final MessageService messageService;

    public MessageSingleton(MessageService messageService) {
        this.messageService = messageService;
    }

    static public boolean isSuccessStatus(HttpStatus status) {
        List<HttpStatus> successStatuses = new ArrayList<>() {{
            add(HttpStatus.OK);
            add(HttpStatus.ACCEPTED);
            add(HttpStatus.CREATED);
            add(HttpStatus.RESET_CONTENT);
        }};

        return successStatuses.contains(status);
    }

    //Authorization
    public ResponseEntity<ResponseData<ResponseMessage>> unauthorized() {
        return prepareResponse(messageService.getMessage(MessageKey.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> userNotFound() {
        return prepareResponse(messageService.getMessage(MessageKey.USER_NOT_FOUND), NOT_FOUND);
    }


    public ResponseEntity<ResponseData<ResponseMessage>> userAlreadyExists() {
        return prepareResponse(messageService.getMessage(MessageKey.USER_ALREADY_EXISTS), HttpStatus.CONFLICT);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> incorrectPhoneNumberAndPassword() {
        return prepareResponse(messageService.getMessage(MessageKey.INCORRECT_USERNANE_OR_PASSWORD), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> userPendingState() {
        return prepareResponse("User is in pending state", HttpStatus.OK);
    }



    //common
    public ResponseEntity<ResponseData<ResponseMessage>> success() {
        return prepareResponse(messageService.getMessage(MessageKey.SUCCESS), HttpStatus.OK);
    }

    public String successMessage() {
        return messageService.getMessage(MessageKey.SUCCESS);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> success(String id) {
        return prepareResponse(id, HttpStatus.OK);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> error() {
        return prepareResponse(messageService.getMessage(MessageKey.ERROR));
    }

    public ResponseEntity<ResponseData<ResponseMessage>> pending() {
        return prepareResponse("pending", HttpStatus.GATEWAY_TIMEOUT);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> accepted() {
        return prepareResponse("Accepted", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> created() {
        return prepareResponse("Created", HttpStatus.CREATED);
    }



//    public ResponseEntity<ResponseData<ResponseMessage>> deviceIsBlocked(BlockedStatus status) {
//        String message = messageService.getMessage(MessageKey.DEVICE_BLOCKED);
//        switch (status) {
//            case MINUTELY: {
//                message = String.format(message, messageService.getMessage(MessageKey.MINUTELY_BLOCKED));
//                break;
//            }
//            case HOURLY: {
//                message = String.format(message, messageService.getMessage(MessageKey.HOURLY_BLOCKED));
//                break;
//            }
//            case DAILY: {
//                message = String.format(message, messageService.getMessage(MessageKey.DAILY_BLOCKED));
//                break;
//            }
//            case FOREVER: {
//                message = String.format(message, "");
//            }
//        }
//        return prepareResponse(message, I_AM_A_TEAPOT);
//    }

    private ResponseEntity<ResponseData<ResponseMessage>> prepareResponse(String message, HttpStatus status) {
        ResponseData<ResponseMessage> response;
        if (isSuccessStatus(status)) {
            response = new ResponseData<>(new ResponseMessage(message));
        } else {
            response = new ResponseData<>(null, message);
        }
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<ResponseData<ResponseMessage>> prepareResponse(String message) {
        return prepareResponse(message, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> prepareResponse(Object object) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", object);
        result.put("message", messageService.getMessage(MessageKey.SUCCESS));
        result.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<?> prepareResponseString(String message, HttpStatus status) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", message);
        result.put("message", "");
        result.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(result, status);
    }

    public ResponseEntity<?> passwordDontMatch() {
        return prepareResponse(messageService.getMessage(MessageKey.PASSWORD_DONT_MATCH), HttpStatus.OK);
    }

    public ResponseEntity<?> userIsBlocked() {
        return prepareResponse(messageService.getMessage(MessageKey.USER_BLOCKED), HttpStatus.OK);
    }

    public ResponseEntity<?> incorrectCredentials() {
        return prepareResponse(messageService.getMessage(MessageKey.INCORRECT_CREDENTIALS), HttpStatus.OK);
    }
}
