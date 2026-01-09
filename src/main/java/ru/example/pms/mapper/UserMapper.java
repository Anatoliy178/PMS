
package ru.example.pms.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.example.pms.dto.UserRegistrationDTO;
import ru.example.pms.model.Role;
import ru.example.pms.model.User;

import java.util.Set;

public class UserMapper {

    public static User fromDTO(UserRegistrationDTO dto, PasswordEncoder encoder, Set<Role> roles) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(encoder.encode(dto.getPassword()));
        user.setActive(true);
        user.setRoles(roles); // теперь Set<Role>
        return user;
    }
}
