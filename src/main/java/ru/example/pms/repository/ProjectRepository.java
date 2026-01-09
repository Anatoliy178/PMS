
package ru.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.pms.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
