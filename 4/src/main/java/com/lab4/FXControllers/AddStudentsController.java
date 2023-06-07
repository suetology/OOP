package com.lab4.FXControllers;

import com.lab4.University.Group;
import com.lab4.University.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddStudentsController {
    @FXML TextField nameField;
    @FXML TextField surnameField;
    @FXML Button addStudentButton;

    private Group currentGroup;

    public void setCurrentGroup(Group group) {
        currentGroup = group;
        addStudentButton.setText("Add to group " + currentGroup.getNumber());
    }

    @FXML
    private void onAddStudentButtonPressed() {
        if (currentGroup == null)
            return;
        if (nameField.getText().length() == 0 || surnameField.getText().length() == 0)
            return;

        Student student = new Student(nameField.getText(), surnameField.getText(), currentGroup.getNumber());
        currentGroup.addStudent(student);

        nameField.clear();
        surnameField.clear();
    }
}
