package com.loancalculator.Controller;

import com.loancalculator.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowController {
    private static Stage _stage;

    public static void init(Stage stage) {
        _stage = stage;
    }

    public static void openStartWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            _stage.setScene(scene);
            _stage.show();
        } catch (Exception e) {

        }
    }

    public static void openResultWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("result-window.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            _stage.setScene(scene);
            _stage.show();
        } catch (Exception e) {

        }
    }
}
