package com.lab3;

import com.lab3.Animals.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML private ComboBox<String> animalChoice;
    @FXML private TextField nameField;
    @FXML private TextField ageField;

    private ResultController resultController;
    private Stage currentStage;
    private Stage nextStage;

    private AnimalData animalData;

    public void setResultController(ResultController resultController) {
        this.resultController = resultController;
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public void setNextStage(Stage stage) {
        this.nextStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> animals = FXCollections.observableArrayList("Cat", "Dog", "Lion", "Spider");
        animalChoice.setItems(animals);
    }

    public void setAnimalData() {
        if (Connection.getInstance().getType() == Connection.Type.SINGLETON) {
            animalData = new AnimalData();
            AnimalDataBridge.getInstance().setAnimalData(animalData);
        } else if (Connection.getInstance().getType() == Connection.Type.USER_DATA) {
            animalData = (AnimalData)currentStage.getUserData();
        } else {
            animalData = new AnimalData();
        }
    }

    @FXML
    public void onAddButtonPressed() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String animalType = animalChoice.getValue();
        Animal animal;

        switch (animalType) {
            case "Spider" -> animal = new Spider(name, age);
            case "Lion" -> animal = new Lion(name, age);
            case "Cat" -> animal = new Cat(name, age);
            case "Dog" -> animal = new Dog(name, age);
            default -> { return; }
        }
        animalData.addAnimal(animal);
        clearFields();
    }

    @FXML
    public void onNextButtonPressed() {
        currentStage.close();
        nextStage.show();

        if (Connection.getInstance().getType() == Connection.Type.CONTROLLER)
            resultController.setAnimalData(animalData);
        else
            resultController.setAnimalData();
    }

    private void clearFields() {
        nameField.clear();
        ageField.clear();
    }
}