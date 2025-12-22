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
