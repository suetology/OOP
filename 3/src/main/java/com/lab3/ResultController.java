package com.lab3;

import com.lab3.Animals.Animal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    @FXML private TableView<Animal> table;

    private AnimalData animalData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Animal, String> nameColumn = new TableColumn<>();
        nameColumn.setPrefWidth(200);
        nameColumn.setText("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Animal, String> ageColumn = new TableColumn<>();
        ageColumn.setPrefWidth(200);
        ageColumn.setText("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Animal, String> typeColumn = new TableColumn<>();
        typeColumn.setPrefWidth(200);
        typeColumn.setText("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        table.getColumns().addAll(nameColumn, ageColumn, typeColumn);
    }

    public void setAnimalData() {
        if (Connection.getInstance().getType() == Connection.Type.SINGLETON)
            animalData = AnimalDataBridge.getInstance().getAnimalData();
        else if (Connection.getInstance().getType() == Connection.Type.USER_DATA)
            animalData = (AnimalData)((Stage)table.getScene().getWindow()).getUserData();
        table.setItems(animalData.getAnimals());
    }

    public void setAnimalData(AnimalData animalData) {
        this.animalData = animalData;
        table.setItems(animalData.getAnimals());
    }
}
