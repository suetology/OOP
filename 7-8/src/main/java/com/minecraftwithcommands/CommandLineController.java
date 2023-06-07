package com.minecraftwithcommands;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CommandLineController {
    @FXML
    private TextField commandLine;

    @FXML
    private void onExecuteButtonPressed() {
        if (commandLine.getText().isBlank())
            return;

        try {
            CommandLine.getInstance().proceedCommand(commandLine.getText());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        commandLine.clear();
    }
}