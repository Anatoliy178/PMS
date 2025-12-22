<<<<<<< HEAD
package ru.example.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;



@Component
public class WelcomeController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    private final int SCENE_WIDTH = 900;
    private final int SCENE_HEIGHT = 700;

    private final ApplicationContext context;
    public Button newTaskButton;

    public WelcomeController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    protected void onStart(ActionEvent event) throws Exception {
        // Получаем ссылку на Stage
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Загружаем основное окно
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        logger.info("Загрузка основного окна");

        stage.setTitle("Project Management System");
        stage.setScene(new Scene(root, 900, 700));
    }
}

=======
package ru.example.pms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;



@Component
public class WelcomeController {

    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    private final int SCENE_WIDTH = 900;
    private final int SCENE_HEIGHT = 700;

    private final ApplicationContext context;
    public Button newTaskButton;

    public WelcomeController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    protected void onStart(ActionEvent event) throws Exception {
        // Получаем ссылку на Stage
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Загружаем основное окно
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        logger.info("Загрузка основного окна");

        stage.setTitle("Project Management System");
        stage.setScene(new Scene(root, 900, 700));
    }
}

>>>>>>> 7374e9df1023a33e84084b72cb55bf9260ed61ed
