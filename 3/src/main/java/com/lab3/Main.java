package com.lab3;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader startFxmlLoader = new FXMLLoader(Main.class.getResource("start-view.fxml"));
        FXMLLoader resultFxmlLoader = new FXMLLoader(Main.class.getResource("result-view.fxml"));
        Scene startScene = new Scene(startFxmlLoader.load());
        Scene resultScene = new Scene(resultFxmlLoader.load());
        StartController startController = startFxmlLoader.getController();
        ResultController resultController = resultFxmlLoader.getController();

        stage.setTitle("Animals");
        stage.setScene(startScene);

        Stage resultStage = new Stage();
        resultStage.setTitle("Animals");
        resultStage.setScene(resultScene);

        startController.setCurrentStage(stage);
        startController.setNextStage(resultStage);
        startController.setResultController(resultController);

        if (Connection.getInstance().getType() == Connection.Type.USER_DATA) {
            AnimalData animalData = new AnimalData();
            stage.setUserData(animalData);
            resultStage.setUserData(animalData);
        }
        startController.setAnimalData();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}