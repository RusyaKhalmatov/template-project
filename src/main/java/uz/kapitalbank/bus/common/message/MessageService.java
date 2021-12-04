package uz.kapitalbank.bus.common.message;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.kapitalbank.bus.common.ThreadLocalSingleton;
import uz.kapitalbank.bus.common.models.Lang;
import uz.kapitalbank.bus.common.models.ResponseData;
import uz.kapitalbank.bus.common.models.ResponseMessage;

import java.util.List;
import java.util.Optional;

import static uz.kapitalbank.bus.common.models.ResponseData.response;


/**
 * @author Rustam Khalmatov
 */
@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public String getMessage(String key) {
        Lang lang = ThreadLocalSingleton.getLang();
        return getMessage(key, lang);
    }

    public String getMessage(String key, Lang lang) {
        Optional<Message> messageOptional = messageRepo.findTopByKeyAndLang(key, lang);
        if (messageOptional.isPresent()) {
            return messageOptional.get().getMessage();
        }
        return key;
    }

    public String getMessageOrEmpty(String key) {
        Lang lang = ThreadLocalSingleton.getLang();
        return getMessageOrEmpty(key, lang);
    }

    public String getMessageOrEmpty(String key, Lang lang) {
        Optional<Message> messageOptional = messageRepo.findTopByKeyAndLang(key, lang);
        if (messageOptional.isPresent()) {
            return messageOptional.map(Message::getMessage).orElse(" ");
        }
        return " ";
    }

//    public ResponseEntity<ResponseData<Map<String, List<Message>>>> getAllMessages() {
//        Map<String, List<Message>> messagesMap = messageRepo.findAll().stream().collect(Collectors.groupingBy(Message::getKey));
//        return response(messagesMap);
//    }

    public ResponseEntity<ResponseData<List<Message>>> getAllMessages() {
        return response(messageRepo.findAll());
    }

    public ResponseEntity<?> addNewMessage(MessageDto messageOld) {
        Optional<Message> messageOptional = messageRepo.findByKeyAndLang(messageOld.getKey(), messageOld.getLang());
        if (messageOptional.isPresent()) {
            return ResponseEntity.ok(new ResponseData<>(new ResponseMessage("Message already exist")));
        }
        Message message = new Message(messageOld.getKey(), messageOld.getLang(), messageOld.getMessage());
        messageRepo.save(message);
        return response(new ResponseData<>(new ResponseMessage("success")));
    }

    public ResponseEntity<?> editMessage(Message messageOld) {
        Optional<Message> messageOptional = messageRepo.findById(messageOld.getId());
        if (messageOptional.isEmpty()) {
            return ResponseEntity.ok(new ResponseData<>(new ResponseMessage("Message not found")));
        }
        Message message = messageOptional.get();
        message.setKey(messageOld.getKey());
        message.setMessage(messageOld.getMessage());
        message.setLang(messageOld.getLang());
        messageRepo.save(message);
        return ResponseEntity.ok(new ResponseData<>(new ResponseMessage("Success")));
    }

    public ResponseEntity<?> deleteMessage(Long id) {
        Optional<Message> messageOptional = messageRepo.findById(id);
        if (messageOptional.isEmpty()) {
            return response(messageOptional.map(Message::getMessage).orElse("Message NOT FOUND"));
        }
        messageRepo.delete(messageOptional.get());
        return response(new ResponseData<>("success"));
    }
}
