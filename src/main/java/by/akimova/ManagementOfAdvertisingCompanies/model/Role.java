package by.akimova.ManagementOfAdvertisingCompanies.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The Role is an enum of user's roles.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

public enum Role implements GrantedAuthority {
    USER(Set.of(Permission.USER_READ)),
    ADMIN(Set.of(Permission.USER_WRITE, Permission.USER_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
