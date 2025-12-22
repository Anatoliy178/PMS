<<<<<<< HEAD
package ru.example.pms.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.example.pms.model.UserRole;

public class RoleUtil {

    public static boolean hasRole(UserRole role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals(role.toAuthority()));
    }
}
=======
package ru.example.pms.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.example.pms.model.UserRole;

public class RoleUtil {

    public static boolean hasRole(UserRole role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        return auth.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals(role.toAuthority()));
    }
}
>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
