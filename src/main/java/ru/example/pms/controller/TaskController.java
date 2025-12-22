<<<<<<< HEAD
package ru.example.pms.controller;


// Импорты для работы с Lombok, Spring и датой
import lombok.RequiredArgsConstructor; // Для автоматического создания конструктора
import org.springframework.format.annotation.DateTimeFormat; // Для форматирования даты
import org.springframework.http.ResponseEntity; // Для формирования HTTP ответа
import org.springframework.web.bind.annotation.*; // Аннотации для REST контроллеров
import ru.example.pms.model.Task; // Модель задачи
import ru.example.pms.model.TaskStatus; // Перечисление статусов задач
import ru.example.pms.service.TaskService; // Сервис для работы с задачами

import java.time.LocalDate;  // Для работы с датой и временем
import java.time.LocalDateTime;
import java.util.Collections; // Утилиты для работы со списками
import java.util.List; // Стандартный список Java

/**
 * @RestController — Spring-аннотация, объединяющая @Controller и @ResponseBody.
 * Все методы возвращают JSON. *
 * @RequestMapping("/tasks") — базовый URL для всех методов контроллера.
 *
 * @RequiredArgsConstructor — Lombok создаёт конструктор с taskService как
 * параметром (внедрение зависимости).
 *
 */
// Аннотация для создания REST контроллера
@RestController
// Определение базового URL для всех эндпоинтов
@RequestMapping("/tasks")
// Lombok аннотация для автоматического создания конструктора
@RequiredArgsConstructor
public class TaskController {

    // Поле для работы с сервисом задач
    // final указывает на неизменяемость ссылки
    private final TaskService taskService;

    /**
     * Метод для создания новой задачи
     *
     * @param task объект задачи, получаемый из тела запроса
     * @return ResponseEntity с созданной задачей и кодом 200 OK
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    /**
     * Метод для получения задач по проекту
     *
     * @param projectId идентификатор проекта
     * @return ResponseEntity со списком задач проекта
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    /**
     * Метод для получения задач по статусу
     *
     * @param status статус задачи (строка)
     * @return ResponseEntity со списком задач указанного статуса
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        try {
            // Преобразование строки в enum
            TaskStatus enumStatus = TaskStatus.valueOf(status.toUpperCase());
            List<Task> tasks = taskService.findByStatus(enumStatus);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException ex) {
            // Обработка неверного статуса
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Метод для поиска задач по ключевому слову
     *
     * @param keyword ключевое слово для поиска
     * @return ResponseEntity со списком найденных задач
     */
    @GetMapping("/tasks/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String keyword) {
        List<Task> tasks = taskService.findByTitleContaining(keyword);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Метод для получения задач с дедлайном до указанной даты
     *
     * @param deadline дата дедлайна
     * @return ResponseEntity со списком задач
     */
    @GetMapping("/tasks/due-before")
    public ResponseEntity<List<Task>> getTasksDueBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline) {

        // Преобразуем LocalDate в LocalDateTime на начало дня
        LocalDateTime deadlineStart = deadline.atStartOfDay();

        List<Task> tasks = taskService.findTasksDueBefore(deadlineStart);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Метод для получения задач по проекту и статусу
     *
     * @param projectId идентификатор проекта
     * @param status статус задачи
     * @return ResponseEntity со списком задач
     */
    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<List<Task>> getByProjectAndStatus(
            @PathVariable Long projectId,
            @PathVariable String status) {
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            List<Task> tasks = taskService.findByStatusAndProjectId(taskStatus, projectId);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }
}

=======
package ru.example.pms.controller;


// Импорты для работы с Lombok, Spring и датой
import lombok.RequiredArgsConstructor; // Для автоматического создания конструктора
import org.springframework.format.annotation.DateTimeFormat; // Для форматирования даты
import org.springframework.http.ResponseEntity; // Для формирования HTTP ответа
import org.springframework.web.bind.annotation.*; // Аннотации для REST контроллеров
import ru.example.pms.model.Task; // Модель задачи
import ru.example.pms.model.TaskStatus; // Перечисление статусов задач
import ru.example.pms.service.TaskService; // Сервис для работы с задачами

import java.time.LocalDate;  // Для работы с датой и временем
import java.time.LocalDateTime;
import java.util.Collections; // Утилиты для работы со списками
import java.util.List; // Стандартный список Java

/**
 * @RestController — Spring-аннотация, объединяющая @Controller и @ResponseBody.
 * Все методы возвращают JSON. *
 * @RequestMapping("/tasks") — базовый URL для всех методов контроллера.
 *
 * @RequiredArgsConstructor — Lombok создаёт конструктор с taskService как
 * параметром (внедрение зависимости).
 *
 */
// Аннотация для создания REST контроллера
@RestController
// Определение базового URL для всех эндпоинтов
@RequestMapping("/tasks")
// Lombok аннотация для автоматического создания конструктора
@RequiredArgsConstructor
public class TaskController {

    // Поле для работы с сервисом задач
    // final указывает на неизменяемость ссылки
    private final TaskService taskService;

    /**
     * Метод для создания новой задачи
     *
     * @param task объект задачи, получаемый из тела запроса
     * @return ResponseEntity с созданной задачей и кодом 200 OK
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    /**
     * Метод для получения задач по проекту
     *
     * @param projectId идентификатор проекта
     * @return ResponseEntity со списком задач проекта
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    /**
     * Метод для получения задач по статусу
     *
     * @param status статус задачи (строка)
     * @return ResponseEntity со списком задач указанного статуса
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        try {
            // Преобразование строки в enum
            TaskStatus enumStatus = TaskStatus.valueOf(status.toUpperCase());
            List<Task> tasks = taskService.findByStatus(enumStatus);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException ex) {
            // Обработка неверного статуса
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Метод для поиска задач по ключевому слову
     *
     * @param keyword ключевое слово для поиска
     * @return ResponseEntity со списком найденных задач
     */
    @GetMapping("/tasks/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String keyword) {
        List<Task> tasks = taskService.findByTitleContaining(keyword);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Метод для получения задач с дедлайном до указанной даты
     *
     * @param deadline дата дедлайна
     * @return ResponseEntity со списком задач
     */
    @GetMapping("/tasks/due-before")
    public ResponseEntity<List<Task>> getTasksDueBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline) {

        // Преобразуем LocalDate в LocalDateTime на начало дня
        LocalDateTime deadlineStart = deadline.atStartOfDay();

        List<Task> tasks = taskService.findTasksDueBefore(deadlineStart);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Метод для получения задач по проекту и статусу
     *
     * @param projectId идентификатор проекта
     * @param status статус задачи
     * @return ResponseEntity со списком задач
     */
    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<List<Task>> getByProjectAndStatus(
            @PathVariable Long projectId,
            @PathVariable String status) {
        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            List<Task> tasks = taskService.findByStatusAndProjectId(taskStatus, projectId);
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }
}

>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
