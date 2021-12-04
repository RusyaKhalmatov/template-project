package uz.kapitalbank.bus.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Rustam Khalmatov
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Transient
    static final String sequenceName = "user_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
    @SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "dateCreated")
    private Timestamp dateCreated = Timestamp.from(Instant.now());

    @Column(name = "dateModified")
    private Timestamp dateModified;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_state")
    private UserState userState;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "organization_name")
    private String orgName;

    @JsonIgnore
    @Column(name = "user_salt")
    private String salt;

    public User(Long id, String username, String password,
                Timestamp dateCreated,
                UserState userState, UserRole role,
                String orgName,String salt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.userState = userState;
        this.role = role;
        this.orgName = orgName;
        this.salt = salt;
    }
}
