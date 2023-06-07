package com.lab4.FXControllers;

import com.lab4.University.Student;
import com.lab4.Util.ActionButtonTableCell;
import com.lab4.University.Course;
import com.lab4.University.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {
    @FXML private TableView<Group> groupTable;
    @FXML private Label errorLabel;

    private FileChooser fileChooser = new FileChooser();
    private ObservableList<Group> groups = FXCollections.observableArrayList();
    private Stage addStudentsStage;
    private Stage setAttendanceStage;
    private AddStudentsController addStudentsController;
    private SetAttendanceController setAttendanceController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Group, Integer> groupIdColumn = new TableColumn<>("Group number");
        groupIdColumn.setPrefWidth(150);
        groupIdColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Group, Integer> studentCountColumn = new TableColumn<>("Students count");
        studentCountColumn.setPrefWidth(150);
        studentCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentCount"));

        TableColumn<Group, Button> addStudentsColumn = new TableColumn<>("Students");
        addStudentsColumn.setPrefWidth(150);
        addStudentsColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Add students", this::onAddStudentsButtonPressed));

        TableColumn<Group, Button> setAttendanceColumn = new TableColumn<>("Attendance");
        setAttendanceColumn.setPrefWidth(148);
        setAttendanceColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Set attendance", this::onSetAttendanceButtonPressed));

        groupTable.getColumns().addAll(groupIdColumn, studentCountColumn, addStudentsColumn, setAttendanceColumn);
    }

    public void setAddStudentsStage(Stage stage) {
        addStudentsStage = stage;
    }

    public void setSetAttendanceStage(Stage stage) {
        setAttendanceStage = stage;
    }

    public void setAddStudentController(AddStudentsController controller) {
        addStudentsController = controller;
    }

    public void setSetAttendanceController(SetAttendanceController setAttendanceController) {
        this.setAttendanceController = setAttendanceController;
    }

    public void setTableItems() {
        ObservableList<Group> groups = FXCollections.observableArrayList(Course.getInstance().getGroups());
        groupTable.setItems(groups);
        updateTable();
    }

    public void updateTable() {
        groupTable.refresh();
    }

    private void onAddStudentsButtonPressed(Group group) {
        addStudentsController.setCurrentGroup(group);
        addStudentsStage.show();
    }

    private void onSetAttendanceButtonPressed(Group group) {
        setAttendanceController.setCurrentGroup(group);
        setAttendanceStage.show();
    }

    @FXML
    private void onAddGroupButtonPressed() {
        Course.getInstance().addGroup();
        setTableItems();
    }

    @FXML
    private void onSaveButtonPressed() {
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null && (file.getName().endsWith(".csv") || file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))) {
            errorLabel.setText("");
            writeToFile(file);
        } else {
            errorLabel.setText("File does not exist or it has wrong extension");
        }
    }

    @FXML
    private void onLoadButtonPressed() {
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null && (file.getName().endsWith(".csv") || file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx"))) {
            errorLabel.setText("");
            readFile(file);
            setTableItems();
        } else {
            errorLabel.setText("File does not exist or it has wrong extension");
        }
    }

    private void writeToFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Group group : Course.getInstance().getGroups()) {
                for (Student student : group.getStudents()) {
                    student.print(printWriter);
                }
            }
            printWriter.close();
        } catch (IOException e) {
            errorLabel.setText("Can't write to a file");
        }
    }

    private void readFile(File file) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                Student student = new Student("", "", 0);
                student.read(line);
            }
        } catch (IOException e) {
            errorLabel.setText("Can't read a file");
        }
    }
}