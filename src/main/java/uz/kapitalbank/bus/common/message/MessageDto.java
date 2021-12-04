package uz.kapitalbank.bus.common.message;


/**
 * @author Rustam Khalmatov
 */

import uz.kapitalbank.bus.common.models.Lang;

public class MessageDto {
    private String key;
    private Lang lang;
    private String message;

    public MessageDto() {
    }

    public MessageDto(String key, Lang lang, String message) {
        this.key = key;
        this.lang = lang;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "key='" + key + '\'' +
                ", lang=" + lang +
                ", message='" + message + '\'' +
                '}';
    }
}
