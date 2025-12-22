<<<<<<< HEAD
package ru.example.pms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Например: ROLE_USER

    public String getName() {
        return name;
    }
}

=======
package ru.example.pms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Например: ROLE_USER

    public String getName() {
        return name;
    }
}

>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
