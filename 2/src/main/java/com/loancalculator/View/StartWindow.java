package com.loancalculator.View;

import com.loancalculator.Controller.LoanCalculator;
import com.loancalculator.Controller.WindowController;
import com.loancalculator.Model.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class StartWindow implements Initializable {
    @FXML private TextField paskolosSuma;
    @FXML private TextField terminasMetai;
    @FXML private TextField terminasMenesiai;
    @FXML private TextField atidejimasMetai;
    @FXML private TextField atidejimasMenesiai;
    @FXML private ComboBox<String> grazinimoGrafikas;
    @FXML private TextField paskolosProcentas;
    @FXML private TextField atidejimoProcentas;
    @FXML private Button skaiciuoti;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> grazinimoGrafikai = FXCollections.observableArrayList("Anuiteto", "Linijinis");
        grazinimoGrafikas.setItems(grazinimoGrafikai);
    }

    public void onSkaiciuotiButtonPressed() {
        if (validateInput())
            WindowController.openResultWindow();
    }

    private boolean validateInput() {
        float suma = 0;
        int terminoMet = 0;
        int terminoMen = 0;
        int atidejimoMet = 0;
        int atidejimoMen = 0;
        Loan.GrazinimoGrafikas grazinimas = Loan.GrazinimoGrafikas.Null;
        float paskolosProc = 0;
        float atidejimoProc = 0;

        try {
            suma = Float.parseFloat(paskolosSuma.getText());
            terminoMet = Integer.parseInt(terminasMetai.getText());
            terminoMen = Integer.parseInt(terminasMenesiai.getText());
            atidejimoMet = Integer.parseInt(atidejimasMetai.getText());
            atidejimoMen = Integer.parseInt(atidejimasMenesiai.getText());
            paskolosProc = Float.parseFloat(paskolosProcentas.getText());
            atidejimoProc = Float.parseFloat(atidejimoProcentas.getText());

            if (grazinimoGrafikas.getValue().equals("Anuiteto"))
                grazinimas = Loan.GrazinimoGrafikas.Anuiteto;
            else if (grazinimoGrafikas.getValue().equals("Linijinis"))
                grazinimas = Loan.GrazinimoGrafikas.Linijinis;
        } catch (Exception e) {
            errorLabel.setText("Blogai įvesti duomenys");
            return false;
        }

        int terminas = terminoMet * 12 + terminoMen;
        int atidejimas = atidejimoMet * 12 + atidejimoMen;

        if (terminas <= 0 || atidejimas < 0 || paskolosProc < 0 || suma <= 0 || grazinimas == Loan.GrazinimoGrafikas.Null) {
            errorLabel.setText("Blogai įvesti duomenys");
            return false;
        }

        LoanCalculator.setPaskola(suma, terminas, atidejimas, grazinimas, paskolosProc, atidejimoProc);
        return true;
    }
}