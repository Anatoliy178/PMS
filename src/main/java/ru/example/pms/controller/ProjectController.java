
package ru.example.pms.controller;

// Импорты для работы с Lombok и Spring
import lombok.RequiredArgsConstructor; // Аннотация для автоматического создания конструктора
import org.springframework.http.ResponseEntity; // Класс для формирования HTTP ответа
import org.springframework.web.bind.annotation.*; // Аннотации для REST контроллеров
import ru.example.pms.model.Project; // Модель проекта
import ru.example.pms.service.ProjectService; // Сервис для работы с проектами

import java.util.List; // Стандартный список Java

// Аннотация для создания REST контроллера
@RestController
// Определение базового URL для всех эндпоинтов
@RequestMapping("/projects")
// Lombok аннотация для автоматического создания конструктора с final полями
@RequiredArgsConstructor
public class ProjectController {

    // Поле для работы с сервисом проектов
    // final указывает на неизменяемость ссылки
    // Внедрение зависимости происходит автоматически через конструктор
    private final ProjectService projectService;

    /**
     * Метод для создания нового проекта
     *
     * @param project объект проекта, получаемый из тела запроса
     * @return ResponseEntity с созданным проектом и кодом 200 OK
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        // Создаем проект через сервис и возвращаем его в ответе
        return ResponseEntity.ok(projectService.createProject(project));
    }

    /**
     * Метод для получения списка всех проектов
     *
     * @return ResponseEntity со списком проектов и кодом 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        // Получаем все проекты через сервис и возвращаем их в ответе
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}

=======
package ru.example.pms.controller;

// Импорты для работы с Lombok и Spring
import lombok.RequiredArgsConstructor; // Аннотация для автоматического создания конструктора
import org.springframework.http.ResponseEntity; // Класс для формирования HTTP ответа
import org.springframework.web.bind.annotation.*; // Аннотации для REST контроллеров
import ru.example.pms.model.Project; // Модель проекта
import ru.example.pms.service.ProjectService; // Сервис для работы с проектами

import java.util.List; // Стандартный список Java


