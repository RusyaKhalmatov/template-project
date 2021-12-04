package uz.kapitalbank.bus.common.message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kapitalbank.bus.common.models.ResponseData;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Rustam Khalmatov
 */

@RestController
@RequestMapping(value = "/api/messages", produces = APPLICATION_JSON_VALUE)
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

//    @AccessControl
    @GetMapping
    public ResponseEntity<ResponseData<List<Message>>> getAllMessages() {
        return messageService.getAllMessages();
    }

//    @AccessControl(UserRole.ADMIN)
    @PostMapping("/add")
    public ResponseEntity<?> addNewMessage(@RequestBody MessageDto message) {
        return messageService.addNewMessage(message);
    }

//    @AccessControl
    @PutMapping("/edit")
    public ResponseEntity<?> editMessage(@RequestBody Message message) {
        return messageService.editMessage(message);
    }

//    @AccessControl(UserRole.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("id") Long id) {
        return messageService.deleteMessage(id);
    }
}
