
package ru.example.pms.controller;

// Импорты для валидации и Spring компонентов
import jakarta.validation.Valid; // Для валидации данных
import org.springframework.beans.factory.annotation.Autowired; // Для внедрения зависимостей
import org.springframework.http.ResponseEntity; // Для формирования HTTP ответа
import org.springframework.web.bind.annotation.*; // Аннотации для REST контроллеров
import ru.example.pms.dto.UserRegistrationDTO; // DTO для регистрации пользователя
import ru.example.pms.service.UserService; // Сервис для работы с пользователями

// Аннотация для создания REST контроллера
@RestController
// Определение базового URL для эндпоинта регистрации
@RequestMapping("/api/register")
public class RegistrationController {

    // Поле для работы с сервисом пользователей
    // final указывает на неизменяемость ссылки
    private final UserService userService;

    /**
     * Конструктор для внедрения зависимости UserService
     *
     * @param userService сервис для работы с пользователями
     */
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод для регистрации нового пользователя
     *
     * @param dto объект с данными для регистрации пользователя
     * @return ResponseEntity с сообщением об успешной регистрации
     */
    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        // Валидация данных происходит автоматически благодаря @Valid
        // Регистрация пользователя через сервис
        userService.register(dto);
        // Возврат успешного ответа
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }
}

=======
package ru.example.pms.controller;

// Импорты для валидации и Spring компонентов
import jakarta.validation.Valid; // Для валидации данных
import org.springframework.beans.factory.annotation.Autowired; // Для внедрения зависимостей
import org.springframework.http.ResponseEntity; // Для формирования HTTP ответа
import org.springframework.web.bind.annotation.*; // Аннотации для REST контроллеров
import ru.example.pms.dto.UserRegistrationDTO; // DTO для регистрации пользователя
import ru.example.pms.service.UserService; // Сервис для работы с пользователями


