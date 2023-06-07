package com.loancalculator;

import com.loancalculator.Controller.WindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Būsto paskolos skaičiuoklė");
        WindowController.init(stage);
        WindowController.openStartWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}