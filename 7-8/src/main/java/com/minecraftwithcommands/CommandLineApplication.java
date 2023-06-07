package com.minecraftwithcommands;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CommandLineApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CommandLineApplication.class.getResource("line-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Command line");
        stage.setScene(scene);
        stage.show();
    }

    public static void start() {
        launch();
    }
}