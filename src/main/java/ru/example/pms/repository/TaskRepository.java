package ru.example.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.pms.model.Task;
import ru.example.pms.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // уже есть
    List<Task> findByProjectId(Long projectId);

    List<Task> findByTitleContainingIgnoreCase(String keyword);


    // добавляем поиск по статусу
    List<Task> findByStatus(TaskStatus status);

    // комбинированный поиск по статусу и ID проекта
    List<Task> findByStatusAndProjectId(TaskStatus status, Long projectId);


    List<Task> findByDeadlineBefore(LocalDateTime deadline);

    // Альтернативный порядок параметров (оба варианта работают)
    // List<Task> findByProjectIdAndStatus(Long projectId, String status);
}


