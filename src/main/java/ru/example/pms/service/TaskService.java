package ru.example.pms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.pms.model.Task;
import ru.example.pms.model.TaskStatus;
import ru.example.pms.repository.TaskRepository;
import ru.example.pms.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Метод для создания задачи через параметры
    public Task createTask(String title, TaskStatus status, LocalDateTime deadline, String description, String assignedTo) {
        Task task = new Task();
        task.setTitle(title);
        task.setStatus(status);
        task.setDeadline(deadline);
        task.setDescription(description);
        task.setAssignedTo(assignedTo);
        return taskRepository.save(task);
    }

    // Дополнительный метод для создания задачи через объект Task
    public Task createTaskFromObject(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task object cannot be null");
        }
        return taskRepository.save(task);
    }

    public List<Task> findByTitleContaining(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Task> findTasksDueBefore(LocalDateTime deadline) {
        return taskRepository.findByDeadlineBefore(deadline);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus( status);
    }

    public List<Task> findByStatusAndProjectId(TaskStatus status, Long projectId) {
        return taskRepository.findByStatusAndProjectId(status, projectId);
    }


    /**
     * Обновить существующую задачу
     *
     * @param task Задача с обновленными данными
     * @return Обновленная задача
     */
    public Task updateTask(Task task) {
        // Проверка существования задачи
        if (taskRepository.existsById(task.getId())) {
            return taskRepository.save(task);
        }
        throw new ResourceNotFoundException("Задача не найдена");
    }

    // Метод для получения задачи по ID
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Задача не найдена с ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task newTask) {
        if (newTask == null) {
            throw new IllegalArgumentException("Задача не может быть пустой");
        }
        return taskRepository.save(newTask);
    }

}