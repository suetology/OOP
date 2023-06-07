package com.lab4;

import com.lab4.FXControllers.AddStudentsController;
import com.lab4.FXControllers.GroupsController;
import com.lab4.FXControllers.SetAttendanceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader startLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene startScene = new Scene(startLoader.load());
        stage.setTitle("Software Engineering course");
        stage.setScene(startScene);
        stage.show();

        FXMLLoader addStudentsLoader = new FXMLLoader(Main.class.getResource("addstudent-view.fxml"));
        Scene addStudentsScene = new Scene(addStudentsLoader.load());
        Stage addStudentsStage = new Stage();
        addStudentsStage.setTitle("Add students");
        addStudentsStage.setScene(addStudentsScene);

        FXMLLoader setAttendanceLoader = new FXMLLoader(Main.class.getResource("setattendance-view.fxml"));
        Scene setAttendanceScene = new Scene(setAttendanceLoader.load());
        Stage setAttendanceStage = new Stage();
        setAttendanceStage.setTitle("Set attendance");
        setAttendanceStage.setScene(setAttendanceScene);

        GroupsController groupsController = startLoader.getController();
        AddStudentsController addStudentController = addStudentsLoader.getController();
        SetAttendanceController setAttendanceController = setAttendanceLoader.getController();

        groupsController.setAddStudentsStage(addStudentsStage);
        groupsController.setSetAttendanceStage(setAttendanceStage);
        groupsController.setAddStudentController(addStudentController);
        groupsController.setSetAttendanceController(setAttendanceController);

        addStudentsStage.setOnHiding(e -> groupsController.updateTable());
    }

    public static void main(String[] args) {
        launch();
    }
}