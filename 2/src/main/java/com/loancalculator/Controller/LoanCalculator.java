package com.loancalculator.Controller;

import com.loancalculator.Model.Loan;
import com.loancalculator.Model.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;

public class LoanCalculator {
    private static Loan paskola;
    private static ObservableList<Payment> paymentData;

    public static void setPaskola(float paskolosSuma, int terminoMen, int atidejimoMen, Loan.GrazinimoGrafikas grazinimoGrafikas, float paskolosProc, float atidejimoProc) {
        paskola = new Loan();
        paskola.setPaskolosSuma(paskolosSuma);
        paskola.setTerminasMen(terminoMen);
        paskola.setAtidejimasMen(atidejimoMen);
        paskola.setGrazinimoGrafikas(grazinimoGrafikas);
        paskola.setPaskolosProcentas(paskolosProc);
        paskola.setAtidejimoProcentas(atidejimoProc);
    }

    public static void setData(TableView<Payment> table, LineChart<Number, Number> chart) {
        DecimalFormat df = new DecimalFormat("0.00");
        paymentData = FXCollections.observableArrayList();

        float sumoketa = 0;

        paymentData.add(new Payment(dateToStr(0), df.format(0f), df.format(0f), df.format(0f), df.format(paskola.getPaskolosSuma()), df.format(sumoketa)));
        int monthsPassed = 1;

        if (paskola.getAtidejimasMen() > 0) {
            float menProcentas = paskola.getAtidejimoProcentas() / (1200f);
            float procentoImoka = paskola.getPaskolosSuma() * menProcentas;

            for (int i = 0; i < paskola.getAtidejimasMen(); i++) {
                String data = dateToStr(monthsPassed + i);
                sumoketa += procentoImoka;
                paymentData.add(new Payment(data, df.format(procentoImoka), df.format(procentoImoka), df.format(0), df.format(paskola.getPaskolosSuma()), df.format(sumoketa)));
            }
        }
        monthsPassed += paskola.getAtidejimasMen();

        if (paskola.getGrazinimoGrafikas() == Loan.GrazinimoGrafikas.Anuiteto) {
            int terminas = paskola.getTerminasMen();
            float suma = paskola.getPaskolosSuma();
            float menProcentas = paskola.getPaskolosProcentas() / (1200f);
            float imoka = suma * (menProcentas / (1 - (float)Math.pow((1 + menProcentas), -terminas)));

            for (int i = 0; i < terminas; i++) {
                String data = dateToStr(monthsPassed + i);
                float procentoImoka = suma * menProcentas;
                suma -= imoka - procentoImoka;
                if (i < terminas - 1) {
                    sumoketa += imoka;
                    paymentData.add(new Payment(data, df.format(imoka), df.format(procentoImoka), df.format(imoka - procentoImoka), df.format(suma), df.format(sumoketa)));
                } else {
                    sumoketa += imoka + suma;
                    paymentData.add(new Payment(data, df.format(imoka + suma), df.format(procentoImoka), df.format(imoka - procentoImoka + suma), df.format(0), df.format(sumoketa)));
                }
            }
        } else {
            int terminas = paskola.getTerminasMen();
            float suma = paskola.getPaskolosSuma();
            float menProcentas = paskola.getPaskolosProcentas() / (1200f);
            float imoka = suma / terminas;

            for (int i = 0; i < terminas; i++) {
                String data = dateToStr(monthsPassed + i);
                float procentoImoka = suma * menProcentas;
                suma -= imoka;
                if (i < terminas - 1) {
                    sumoketa += imoka + procentoImoka;
                    paymentData.add(new Payment(data, df.format(imoka + procentoImoka), df.format(procentoImoka), df.format(imoka), df.format(suma), df.format(sumoketa)));
                } else {
                    sumoketa += imoka + suma;
                    paymentData.add(new Payment(data, df.format(imoka + suma), df.format(procentoImoka), df.format(imoka - procentoImoka + suma), df.format(0), df.format(sumoketa)));
                }
            }
        }
        setTable(table, paymentData);
        setChart(chart, paymentData);
    }

    public static void filterData(TableView<Payment> table, LineChart<Number, Number> chart, int nuoMetai, int nuoMenuo, int ikiMetai, int ikiMenuo) {
        Date date = new Date();
        int year = 1900 + date.getYear();
        int month = date.getMonth();

        int loanStart = 0;
        int loanEnd = paskola.getAtidejimasMen() + paskola.getTerminasMen() + 1;

        int filterStart = (nuoMetai - year) * 12 + nuoMenuo - month - 1;
        int filterEnd = (ikiMetai - year) * 12 + ikiMenuo - month;

        if (filterStart > filterEnd || filterStart > loanEnd || filterEnd < loanStart)
            return;

        if (filterStart < loanStart)
            filterStart = loanStart;
        if (filterEnd > loanEnd)
            filterEnd = loanEnd;

        ObservableList<Payment> filteredData = FXCollections.observableArrayList();
        for (int i = filterStart; i < filterEnd; i++) {
            filteredData.add(paymentData.get(i));
        }
        setTable(table, filteredData);
        setChart(chart, filteredData);
    }

    private static void setTable(TableView<Payment> table, ObservableList<Payment> data) {
        table.setItems(data);
    }

    private static void setChart(LineChart<Number, Number> chart, ObservableList<Payment> data) {
        chart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Mokėjimai");

        for (int i = 1; i < data.size(); i++)
            series.getData().add(new XYChart.Data<>(i, Float.parseFloat(data.get(i).getMokejimas())));

        chart.getData().add(series);
    }

    public static void outputDataToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("Mokėjimai.csv");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Payment payment : paymentData)
            printWriter.printf("%s,%s,%s,%s,%s,%s\n", payment.getData(), payment.getMokejimas(), payment.getProcentoImoka(), payment.getPaskolosKunas(), payment.getPaskolosLikutis(), payment.getSumoketa());
        printWriter.close();
    }

    private static String dateToStr(int monthsPassed) {
        StringBuilder strDate = new StringBuilder();
        Date date = new Date();
        int year = 1900 + date.getYear();
        int month = date.getMonth() + monthsPassed;
        if (month >= 12) {
            year += month / 12;
        }
        month = month % 12 + 1;
        strDate.append(year);
        if (month < 10)
            strDate.append(".0" + month);
        else
            strDate.append("." + month);

        return strDate.toString();
    }
}
