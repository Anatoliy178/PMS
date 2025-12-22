<<<<<<< HEAD
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

=======
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

>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
