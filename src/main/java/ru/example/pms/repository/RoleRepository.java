<<<<<<< HEAD
package ru.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.pms.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}

=======
package ru.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.pms.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}

>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
