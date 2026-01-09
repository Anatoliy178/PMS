
package ru.example.pms.model;

public enum UserRole {
    ADMIN,
    SUPERVISOR,
    MANAGER,
    USER;

    public String toAuthority() {
        return "ROLE_" + this.name();
    }
}

