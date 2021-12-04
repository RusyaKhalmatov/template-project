package uz.kapitalbank.bus.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Rustam Khalmatov
 */


public enum UserRole {
    ADMIN(Set.of(Permission.ADMIN_USER)),
    SUPER_ADMIN(Set.of(Permission.SUPER_ADMIN_USER)),
    CLIENT(Set.of(Permission.CLIENT_USER));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
