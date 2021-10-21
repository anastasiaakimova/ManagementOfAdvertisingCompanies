package by.akimova.ManagementOfAdvertisingCompanies.model;

/**
 * The Permission is an enum of user's access to the requests.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}