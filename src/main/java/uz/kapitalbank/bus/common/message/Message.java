package uz.kapitalbank.bus.common.message;


import uz.kapitalbank.bus.common.models.Lang;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rustam Khalmatov
 */

@Entity
@Table(name = "messages", uniqueConstraints = {
        @UniqueConstraint(name = "unique_message", columnNames = {"key", "lang"})
})
public class Message implements Serializable {
    @Transient
    static final String sequenceName = "message_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Column(name = "message")
    private String message;

    public Message() {
    }

    public Message(String key, Lang lang, String message) {
        this.key = key;
        this.lang = lang;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
