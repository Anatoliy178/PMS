package ru.example.pms.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.pms.model.TaskStatus;
import ru.example.pms.model.UserRole;
import ru.example.pms.model.Task;
import ru.example.pms.service.TaskService;
import ru.example.pms.util.RoleUtil;
import ru.example.pms.util.ThemeManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
//import static ru.example.pms.util.CellFactories.dueDateCellFactory;

@Component // Позволяет Spring автоматически управлять этим контроллером
public class MainController implements Initializable {

    // 🎯 Основной layout
    @FXML private BorderPane rootPane;

    // 📊 Колонки таблицы задач
    @FXML private TableColumn<Task, Long> colId;               // ID задачи
    @FXML private TableColumn<Task, String> colName;           // Название
    @FXML private TableColumn<Task, String> colStatus;         // Статус
    @FXML private TableColumn<Task, LocalDateTime> colDueDate; // Дедлайн
    @FXML private TableColumn<Task, LocalDateTime> colCreatedAt; // Дата создания
    @FXML private TableColumn<Task, Void> colActions;          // Кнопки действий

    // 🔍 Фильтр по статусу
    @FXML private ComboBox<String> statusFilter;

    // ➕ Кнопка создания новой задачи
    @FXML private Button newTaskButton;

    // 🧠 Сервис для работы с задачами
    @FXML private TaskService taskService;

    // 📋 Таблица задач
    @FXML private TableView<Task> taskTable;

    // 📦 Хранилище задач
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    // 💉 Внедрение зависимости через конструктор
    @Autowired
    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @FXML
    public void initialize() {
        // 1️⃣ Проверка прав доступа
        boolean canCreate = RoleUtil.hasRole(UserRole.ADMIN) || RoleUtil.hasRole(UserRole.MANAGER);
        newTaskButton.setVisible(canCreate); // Скрываем кнопку, если нет прав

        // 2️⃣ Применение темы оформления
        applyThemeOnLoad();

        // 3️⃣ Настройка колонок таблицы
        setupColumns();

        // 4️⃣ Настройка фильтра статуса
        statusFilter.getItems().addAll("ALL", "OPEN", "IN_PROGRESS", "DONE");
        statusFilter.setValue("IN_PROGRESS");
        statusFilter.setOnAction(e -> applyFilter());

        // 5️⃣ Загрузка задач
        loadTasks(1L); // TODO: метод закомментирован — нужно вернуть или реализовать

        // 6️⃣ Обработка кнопки создания задачи
        newTaskButton.setOnAction(e -> onNewTask());
    }

    private void applyThemeOnLoad() {
        // Применяем тему при загрузке сцены
        if (rootPane.getScene() != null) {
            ThemeManager.applyTheme(rootPane.getScene());
        }

        // Слушаем смену сцены
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                ThemeManager.applyTheme(newScene);
                loadSavedTheme(newScene);
            }
        });
    }

    private void loadSavedTheme(Scene scene) {
        // Загружаем сохранённую тему из Preferences
        Preferences prefs = Preferences.userNodeForPackage(MainController.class);
        String theme = prefs.get("theme", "light.css");
        URL url = getClass().getResource("/css/" + theme);
        if (url != null) {
            scene.getStylesheets().add(url.toExternalForm());
        } else {
            scene.getStylesheets().add(
                    getClass().getResource("/css/light.css").toExternalForm()
            );
        }
    }

    private void setupColumns() {
        // Настройка отображения данных в колонках
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("title"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Форматирование даты дедлайна
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        colDueDate.setCellFactory(dueDateCellFactory()); // TODO: убедиться, что фабрика реализована

        // Форматирование даты создания
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colCreatedAt.setCellFactory(createdAtCellFactory());

        // Кнопки действий (редактировать / удалить)
        colActions.setCellFactory(actionCellFactory());

        // Дополнительная колонка: кому назначено
        TableColumn<Task, String> assignedToColumn = new TableColumn<>("Назначено");
        assignedToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        taskTable.getColumns().add(assignedToColumn);
    }

    private void applyFilter() {
        // Применяем фильтр по статусу
        String filter = statusFilter.getValue();
        taskTable.setItems(tasks.filtered(task ->
                "IN_PROGRESS".equals(filter) || task.getStatus().name().equals(filter)
        ));
    }

    private void onNewTask() {
        // Создание диалога для новой задачи
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Создать новую задачу");
        dialog.setHeaderText("Заполните информацию о задаче");

        VBox dialogContent = new VBox(10);
        dialogContent.setStyle("-fx-padding: 15;");

        // Поля формы
        TextField titleField = new TextField();
        titleField.setPromptText("Название задачи");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Описание задачи");
        descriptionArea.setPrefHeight(80);

        DatePicker dueDatePicker = new DatePicker(LocalDate.now().plusDays(7));
        dueDatePicker.setPromptText("Срок выполнения");

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("NEW", "IN_PROGRESS", "DONE");
        statusComboBox.setValue("NEW");

        TextField assignedToField = new TextField();
        assignedToField.setPromptText("Кому назначена задача");

        // Добавляем поля в диалог
        dialogContent.getChildren().addAll(
                new Label("Название:"), titleField,
                new Label("Описание:"), descriptionArea,
                new Label("Срок:"), dueDatePicker,
                new Label("Статус:"), statusComboBox,
                new Label("Назначено:"), assignedToField
        );

        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Проверка перед сохранением
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                if (titleField.getText().trim().isEmpty()) {
                    showError("Название задачи не может быть пустым!");
                    return null;
                }
                return button;
            }
            return null;
        });

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Создаём новую задачу
            Task newTask = new Task();
            newTask.setTitle(titleField.getText());
            newTask.setDescription(descriptionArea.getText());
            newTask.setDeadline(LocalDateTime.of(dueDatePicker.getValue(), LocalTime.MIDNIGHT));
            newTask.setStatus(TaskStatus.valueOf(statusComboBox.getValue()));
            newTask.setAssignedTo(assignedToField.getText());

            try {
                Task savedTask = taskService.createTask(newTask);
                tasks.add(savedTask);
                showSuccess("Задача успешно создана!");
            } catch (Exception e) {
                showError("Ошибка при создании задачи: " + e.getMessage());
            }
        }
    }

    // TODO: Реализовать методы showError и showSuccess, если они ещё не определены
    // TODO: Вернуть метод loadTasks(), если он нужен для загрузки задач при старте

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Операция выполнена успешно");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Произошла ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Callback<TableColumn<Task, LocalDateTime>, TableCell<Task, LocalDateTime>> dueDateCellFactory() {
        return col -> new TableCell<>() {
            private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : date.format(fmt));
            }
        };
    }

    private Callback<TableColumn<Task, LocalDateTime>, TableCell<Task, LocalDateTime>> createdAtCellFactory() {
        return col -> new TableCell<>() {
            private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : date.format(fmt));
            }
        };
    }

    private Callback<TableColumn<Task, Void>, TableCell<Task, Void>> actionCellFactory() {
        return col -> new TableCell<>() {
            private final Button editBtn = new Button("Редактировать");
            private final Button delBtn  = new Button("Удалить");
            private final HBox pane      = new HBox(5, editBtn, delBtn);

            {
                editBtn.getStyleClass().add("edit-button");
                delBtn.getStyleClass().add("delete-button");

                editBtn.setOnAction(e -> {
                    Task t = getTableView().getItems().get(getIndex());
                    onEditTask(t);
                });
                delBtn.setOnAction(e -> {
                    Task t = getTableView().getItems().get(getIndex());
                    onDeleteTask(t);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
    }

    private void onDeleteTask(Task task) {
        taskService.deleteTask(task.getId());
        tasks.remove(task);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // связываем таблицу со списком
        taskTable.setItems(tasks);
        loadTasks(1L);
    }



    /**
     * Загрузите задачи с уровня обслуживания и обновите представление таблицы
     */
    private void loadTasks(Long projectId) {
        try {
            // Получаем список задач
            List<Task> list = taskService.getTasksByProject(projectId);

            // Проверяем результат
            if (list == null) {
                showError("Не удалось загрузить задачи");
                return;
            }

            // Обновляем таблицу в UI потоке
            Platform.runLater(() -> {
                tasks.setAll(list);
            });
        } catch (Exception e) {
            showError("Произошла ошибка при загрузке задач: " + e.getMessage());
        }
    }


    /**
     * Создать клеточную фабрику для кнопок действия (редактировать/удалить)
     *
     * @return TableCell Factory для кнопок действия
     */
    private Callback<TableColumn<Task, Task>, TableCell<Task, Task>> getActionCellFactory() {
        return col -> new TableCell<>() {
            private final Button edit = new Button("Edit");
            private final Button del = new Button("Del");
            private final HBox pane = new HBox(5, edit, del);

            {
                edit.getStyleClass().add("icon-button");
                del.getStyleClass().add("icon-button");
                edit.setOnAction(e -> onEditTask(getItem()));
                del.setOnAction(e -> onDelete(getItem()));
            }

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                setGraphic(empty || task == null ? null : pane);
            }
        };
    }

    @FXML
    private void onNewTask(ActionEvent e) {
        // Диалог для названия задачи
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Новая задача");
        nameDialog.setHeaderText("Введите название задачи:");
        Optional<String> nameResult = nameDialog.showAndWait();

        nameResult.ifPresent(name -> {
            // Диалог для назначения задачи
            TextInputDialog assignedDialog = new TextInputDialog();
            assignedDialog.setTitle("Назначение задачи");
            assignedDialog.setHeaderText("Кому предназначена задача?");
            Optional<String> assignedResult = assignedDialog.showAndWait();

            assignedResult.ifPresent(assignedTo -> {
                Task task = taskService.createTask(
                        name,
                        TaskStatus.NEW,
                        LocalDateTime.now().plusDays(7),
                        "Описание задачи",
                        assignedTo
                );
                taskTable.getItems().add(task);
            });
        });
    }


    private void applySavedTheme(Scene scene) {
        Preferences prefs = Preferences.userNodeForPackage(MainController.class);
        String savedTheme = prefs.get("theme", "light.css");

        URL cssUrl = getClass().getResource("/css/" + savedTheme);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("⚠️ Тема не найдена: " + savedTheme);
        }
    }


    /**
     * Обрабатывать запрос на редактирование задач
     *
     * @param task Task будет редактироваться
     */
    private void onEditTask(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditTaskDialog.fxml"));
            DialogPane dialogPane = loader.load();

            EditTaskController controller = loader.getController();
            controller.setTask(task);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Редактировать задачу");

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Task updated = controller.getUpdatedTask();
                taskService.updateTask(updated); // добавьте этот метод в TaskService
                loadTasks(1L);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle task deletion request
     *
     * @param task Task to be deleted
     */
    private void onDelete(Task task) {
        taskService.deleteTask(task.getId());
        loadTasks(1L);
    }


    /**
     * Handle status filter change
     *
     * @param e Action event from UI
     */
    @FXML
    private void onFilter(ActionEvent e) {
        String status = statusFilter.getValue();
        if ("IN_PROGRESS".equals(status)) {
            loadTasks(1L);
        } else {
            tasks.setAll(taskService.findByStatus(TaskStatus.valueOf(status)));
        }
    }
// Смена темы оформления
    @FXML
    private void onToggleTheme() {
        Scene scene = rootPane.getScene();
        if (scene == null) return;

        String current = scene.getStylesheets().isEmpty() ? "" : scene.getStylesheets().get(0);
        String newTheme = current.contains("light.css") ? "dark.css" : "light.css";

        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource("/css/" + newTheme).toExternalForm());

        Preferences prefs = Preferences.userNodeForPackage(MainController.class);
        prefs.put("theme", newTheme);
    }
}

