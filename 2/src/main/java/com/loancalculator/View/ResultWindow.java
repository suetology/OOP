package com.loancalculator.View;

import com.loancalculator.Controller.LoanCalculator;
import com.loancalculator.Model.Loan;
import com.loancalculator.Model.Payment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultWindow implements Initializable {
    @FXML private LineChart<Number, Number> grafikas;
    @FXML private TableView<Payment> table;

    @FXML private TextField nuoMetai;
    @FXML private TextField nuoMenuo;
    @FXML private TextField ikiMetai;
    @FXML private TextField ikiMenuo;
    @FXML private Button filtras;
    @FXML private Button ataskaita;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn dateColumn = new TableColumn("Data");
        dateColumn.setPrefWidth(130);
        dateColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("data"));

        TableColumn paymentColumn = new TableColumn("Mokėjimas");
        paymentColumn.setPrefWidth(130);
        paymentColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("mokejimas"));

        TableColumn percentColumn = new TableColumn("Procentai");
        percentColumn.setPrefWidth(130);
        percentColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("procentoImoka"));

        TableColumn loanColumn = new TableColumn("Paskolos kūnas");
        loanColumn.setPrefWidth(130);
        loanColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("paskolosKunas"));

        TableColumn remainderColumn = new TableColumn("Liekana");
        remainderColumn.setPrefWidth(130);
        remainderColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("paskolosLikutis"));

        TableColumn paidColumn = new TableColumn("Sumokėta");
        paidColumn.setPrefWidth(130);
        paidColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("sumoketa"));

        table.getColumns().addAll(dateColumn, paymentColumn, percentColumn, loanColumn, remainderColumn, paidColumn);
        LoanCalculator.setData(table, grafikas);
    }

    public void onAtaskaitaButtonPressed() {
        try {
            LoanCalculator.outputDataToFile();
        } catch (Exception e) {
            System.err.println("Cannot export data");
        }
    }

    public void onFiltrasButtonPressed() {
        validateInput();
    }

    private void validateInput() {
        int nuoMet = 0;
        int nuoMen = 0;
        int ikiMet = 0;
        int ikiMen = 0;

        try {
            nuoMet = Integer.parseInt(nuoMetai.getText());
            nuoMen = Integer.parseInt(nuoMenuo.getText());
            ikiMet = Integer.parseInt(ikiMetai.getText());
            ikiMen = Integer.parseInt(ikiMenuo.getText());
        } catch (Exception e) {
            return;
        }

        if (nuoMet <= 0 || nuoMen <= 0 || nuoMen > 12 || ikiMet <= 0 || ikiMen <= 0 || ikiMen > 12) {
            return;
        }
        LoanCalculator.filterData(table, grafikas, nuoMet, nuoMen, ikiMet, ikiMen);
    }
}
