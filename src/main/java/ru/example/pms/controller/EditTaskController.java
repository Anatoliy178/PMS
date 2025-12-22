<<<<<<< HEAD
package ru.example.pms.controller;

// Импортируем компоненты JavaFX и модели
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.example.pms.model.Task;
import ru.example.pms.model.TaskStatus;
import ru.example.pms.model.User;
import ru.example.pms.service.UserService;

import java.util.List;

public class EditTaskController {

    // 🔧 UI-компоненты, связанные с FXML
    @FXML private TextField nameField;         // Поле для названия задачи
    @FXML private ComboBox<String> statusBox;  // Выпадающий список статусов
    @FXML private DatePicker dueDatePicker;    // Выбор даты дедлайна
    @FXML private TextArea descField;          // Описание задачи
    @FXML private ComboBox<User> assigneeBox;  // Выпадающий список исполнителей

    private UserService userService;           // Сервис для получения пользователей

    // Устанавливаем сервис извне (например, через DI или вручную)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private Task task; // Задача, которую редактируем

    @FXML
    public void initialize() {
        // Инициализация списка пользователей, если сервис доступен
        if (userService != null) {
            List<User> users = userService.getAllUsers();
            assigneeBox.setItems(FXCollections.observableArrayList(users));
        }

        // Настраиваем отображение имени пользователя в списке
        assigneeBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getName());
            }
        });

        // Настраиваем отображение выбранного пользователя в ComboBox
        assigneeBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getName());
            }
        });
    }

    // Заполняем поля данными задачи
    public void setTask(Task task) {
        this.task = task;
        nameField.setText(task.getTitle());
        statusBox.getItems().addAll("OPEN", "IN_PROGRESS", "DONE"); // TODO: можно заменить на enum.values()
        statusBox.setValue(String.valueOf(task.getStatus()));
        dueDatePicker.setValue(task.getDeadline().toLocalDate());
        descField.setText(task.getDescription());
        assigneeBox.setValue(task.getAssignee());
    }

    // Получаем обновлённую задачу из UI
    public Task getUpdatedTask() {
        task.setTitle(nameField.getText());
        task.setDeadline(dueDatePicker.getValue().atStartOfDay());
        task.setDescription(descField.getText());

        // Преобразуем строку в enum, с защитой от ошибок
        try {
            task.setStatus(TaskStatus.valueOf(statusBox.getValue()));
        } catch (IllegalArgumentException | NullPointerException e) {
            task.setStatus(TaskStatus.NEW); // TODO: уточнить, нужно ли сбрасывать статус или оставить прежний
        }

        task.setAssignee(assigneeBox.getValue());
        return task;
    }
}
=======
package ru.example.pms.controller;

// Импортируем компоненты JavaFX и модели
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.example.pms.model.Task;
import ru.example.pms.model.TaskStatus;
import ru.example.pms.model.User;
import ru.example.pms.service.UserService;

import java.util.List;

public class EditTaskController {

    // 🔧 UI-компоненты, связанные с FXML
    @FXML private TextField nameField;         // Поле для названия задачи
    @FXML private ComboBox<String> statusBox;  // Выпадающий список статусов
    @FXML private DatePicker dueDatePicker;    // Выбор даты дедлайна
    @FXML private TextArea descField;          // Описание задачи
    @FXML private ComboBox<User> assigneeBox;  // Выпадающий список исполнителей

    private UserService userService;           // Сервис для получения пользователей

    // Устанавливаем сервис извне (например, через DI или вручную)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private Task task; // Задача, которую редактируем

    @FXML
    public void initialize() {
        // Инициализация списка пользователей, если сервис доступен
        if (userService != null) {
            List<User> users = userService.getAllUsers();
            assigneeBox.setItems(FXCollections.observableArrayList(users));
        }

        // Настраиваем отображение имени пользователя в списке
        assigneeBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getName());
            }
        });

        // Настраиваем отображение выбранного пользователя в ComboBox
        assigneeBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getName());
            }
        });
    }

    // Заполняем поля данными задачи
    public void setTask(Task task) {
        this.task = task;
        nameField.setText(task.getTitle());
        statusBox.getItems().addAll("OPEN", "IN_PROGRESS", "DONE"); // TODO: можно заменить на enum.values()
        statusBox.setValue(String.valueOf(task.getStatus()));
        dueDatePicker.setValue(task.getDeadline().toLocalDate());
        descField.setText(task.getDescription());
        assigneeBox.setValue(task.getAssignee());
    }

    // Получаем обновлённую задачу из UI
    public Task getUpdatedTask() {
        task.setTitle(nameField.getText());
        task.setDeadline(dueDatePicker.getValue().atStartOfDay());
        task.setDescription(descField.getText());

        // Преобразуем строку в enum, с защитой от ошибок
        try {
            task.setStatus(TaskStatus.valueOf(statusBox.getValue()));
        } catch (IllegalArgumentException | NullPointerException e) {
            task.setStatus(TaskStatus.NEW); // TODO: уточнить, нужно ли сбрасывать статус или оставить прежний
        }

        task.setAssignee(assigneeBox.getValue());
        return task;
    }
}
>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
