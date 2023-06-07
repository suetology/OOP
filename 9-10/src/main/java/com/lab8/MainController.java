package com.lab8;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    private final Database database = Database.getInstance();

    @FXML private ComboBox<String> filterCombo;
    @FXML private TableView<Data> table;
    @FXML private TextField filterField;
    @FXML private Label recordsCount;
    @FXML private ComboBox<String> sortingOrderCombo;
    @FXML private ComboBox<String> sortByCombo;
    @FXML private ComboBox<String> changeTargetCombo;
    @FXML private ComboBox<String> changeToCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupComboBoxes();
        setupTable();

        ObservableList<Data> observableData = database.getObservableData();
        observableData.addListener((ListChangeListener<Data>) change -> recordsCount.setText(observableData.size() + " records"));
    }

    private void setupComboBoxes() {
        ObservableList<String> filterOptions = FXCollections.observableArrayList(List.of("First Name", "Last Name", "Email", "Image Link", "IP Address"));
        filterCombo.setItems(filterOptions);

        ObservableList<String> sortingOrderOptions = FXCollections.observableArrayList(List.of("Descending", "Ascending"));
        sortingOrderCombo.setItems(sortingOrderOptions);

        ObservableList<String> sortByOptions = FXCollections.observableArrayList(List.of("First Name", "Last Name", "Email", "Image Link", "IP Address"));
        sortByCombo.setItems(sortByOptions);

        ObservableList<String> changeTargetOptions = FXCollections.observableArrayList(List.of("First Name", "Last Name"));
        changeTargetCombo.setItems(changeTargetOptions);

        ObservableList<String> changeToOptions = FXCollections.observableArrayList(List.of("Lowercase", "Uppercase", "Capitalize"));
        changeToCombo.setItems(changeToOptions);
    }

    private void setupTable() {
        TableColumn<Data, String> firstNameColumn = createColumn("First Name", 150, "firstName");
        TableColumn<Data, String> lastNameColumn = createColumn("Last Name", 150, "lastName");
        TableColumn<Data, String> emailColumn = createColumn("Email", 150, "email");
        TableColumn<Data, String> imageLinkColumn = createColumn("Image Link", 150, "imageLink");
        TableColumn<Data, String> ipAddressColumn = createColumn("IP Address", 150, "ipAddress");

        table.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, imageLinkColumn, ipAddressColumn);
        table.setItems(database.getObservableData());
    }

    private static <T, R> TableColumn<T, R> createColumn(String name, int width, String propertyName) {
        TableColumn<T, R> column = new TableColumn<>(name);
        column.setPrefWidth(width);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
    }

    @FXML
    private void onFilterButtonPressed() {
        if (filterField.getText() == null || filterCombo.getValue() == null ||
            filterField.getText().isBlank() || filterCombo.getValue().isBlank())
            return;

        String filterTarget = filterCombo.getValue().toLowerCase();
        String filterText = filterField.getText().toLowerCase();

        database.filterData(filterTarget, filterText);
    }

    @FXML
    private void onClearFilterButtonPressed() {
        database.resetData();
        filterField.clear();
        filterCombo.setValue(null);
        filterCombo.setPromptText("Filter target");
    }

    @FXML
    private void onSortButtonPressed() {
        String order = sortingOrderCombo.getValue();
        String sortBy = sortByCombo.getValue();

        if (order == null || sortBy == null)
            return;

        database.sortData(order, sortBy);
    }

    @FXML
    private void onChangeButtonPressed() {
        String changeTarget = changeTargetCombo.getValue();
        String changeTo = changeToCombo.getValue();

        if (changeTarget == null || changeTo == null)
            return;

        database.changeData(changeTarget, changeTo);
    }
}