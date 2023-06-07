package com.lab4.FXControllers;

import com.lab4.University.Group;
import com.lab4.University.Lectures;
import com.lab4.University.Student;
import com.lab4.Util.PDFMaker;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

//import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Function;

public class SetAttendanceController implements Initializable {
    @FXML private TableView<Student> table;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;

    private Group currentGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Student, String> studentsColumn = new TableColumn<>("Student");
        studentsColumn.setPrefWidth(200);
        studentsColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        table.getColumns().add(studentsColumn);

        setAttendanceColumns(0, Lectures.COUNT - 1);
    }

    public void setCurrentGroup(Group group) {
        currentGroup = group;
        ((Stage)table.getScene().getWindow()).setTitle("Group " + currentGroup.getNumber() + " attendance");

        setStudentsToTable(group.getStudents());
    }

    private void setStudentsToTable(ArrayList<Student> students) {
        ObservableList<Student> observableStudents = FXCollections.observableArrayList(students);
        table.setItems(observableStudents);
    }

    private void setAttendanceColumns(int from, int to) {
        if (from > to || to < 0)
            return;

        if (table.getColumns().size() > 1)
            table.getColumns().remove(1, table.getColumns().size());

        TableColumn<Student, CheckBox>[] attendanceColumns = new TableColumn[to - from + 1];
        for (int i = 0; i < attendanceColumns.length; i++) {
            TableColumn<Student, CheckBox> col = new TableColumn<>("L" + (i + from + 1));
            col.setPrefWidth(40);
            col.setCellValueFactory(createArrayValueFactory(Student::getAttendance, i + from));

            attendanceColumns[i] = col;
        }
        table.getColumns().addAll(attendanceColumns);
    }

    @FXML
    private void onFilterButtonPressed() {
        int from, to;
        try {
            from = Integer.parseInt(fromField.getText());
            to = Integer.parseInt(toField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        setAttendanceColumns(from - 1, to - 1);
    }

    @FXML
    private void onFindButtonPressed() {
        String name = nameField.getText();
        String surname = surnameField.getText();

        if (name.equals("") && surname.equals("")) {
            setStudentsToTable(currentGroup.getStudents());
            return;
        }

        ArrayList<Student> searchedStudents = new ArrayList<>();
        for (Student student : currentGroup.getStudents())
            if (student.getName().toLowerCase().startsWith(name.toLowerCase()) &&
                student.getSurname().toLowerCase().startsWith(surname.toLowerCase()))
                searchedStudents.add(student);

        setStudentsToTable(searchedStudents);
    }

    @FXML
    private void onClearButtonPressed() {
        fromField.clear();
        toField.clear();
        nameField.clear();
        surnameField.clear();
        setStudentsToTable(currentGroup.getStudents());
        setAttendanceColumns(0, Lectures.COUNT - 1);
    }

    @FXML
    private void onSaveButtonPressed() {
            PDFMaker.groupToPDF(currentGroup);
    }

    private <S, T> Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>> createArrayValueFactory(Function<S, T[]> arrayExtractor, final int index) {
        if (index < 0) {
            return cd -> null;
        }
        return cd -> {
            T[] array = arrayExtractor.apply(cd.getValue());
            return array == null || array.length <= index ? null : new SimpleObjectProperty<>(array[index]);
        };
    }
}
