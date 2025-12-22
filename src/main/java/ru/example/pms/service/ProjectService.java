<<<<<<< HEAD
package ru.example.pms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.pms.model.Project;
import ru.example.pms.repository.ProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
=======
package ru.example.pms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.pms.model.Project;
import ru.example.pms.repository.ProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
