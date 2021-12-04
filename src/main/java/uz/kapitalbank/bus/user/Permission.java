package uz.kapitalbank.bus.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Rustam Khalmatov
 */

@Getter
public enum Permission {
    ADMIN_USER("user:admin-user"),
    SUPER_ADMIN_USER("user:super-admin-user"),
    CLIENT_USER("user:client-user");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
