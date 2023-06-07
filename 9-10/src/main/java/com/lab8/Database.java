package com.lab8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Database {
    private static Database instance;
    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private final String DATA_FILENAME = "MOCK_DATA.csv";
    private final Map<String, Data> ipDataMap = new HashMap<>();

    private final List<Data> data = new ArrayList<>();
    private final ObservableList<Data> observableData = FXCollections.observableArrayList();

    private Database() {
        readCSV();
        observableData.addAll(data);
    }

    public ObservableList<Data> getObservableData() {
        return observableData;
    }

    public void resetData() {
        observableData.setAll(data);
    }

    public void filterData(String filterTarget, String filterText) {
        Predicate<Data> filter;
        switch (filterTarget) {
            case "first name" -> filter = d -> d.getFirstName().toLowerCase().startsWith(filterText);
            case "last name" -> filter = d -> d.getLastName().toLowerCase().startsWith(filterText);
            case "email" -> filter = d -> d.getEmail().toLowerCase().startsWith(filterText);
            case "image link" -> filter = d -> d.getImageLink().toLowerCase().startsWith(filterText);
            case "ap address" -> filter = d -> d.getIpAddress().toLowerCase().startsWith(filterText);
            default -> { return; }
        }

        Stream<Data> dataStream = observableData.stream().filter(filter);
        observableData.setAll(dataStream.toList());
    }

    public void sortData(String order, String sortBy) {
        Comparator<Data> comparator;
        switch (order) {
            case "Ascending" -> {
                switch (sortBy) {
                    case "First Name" -> comparator = Comparator.comparing(Data::getFirstName);
                    case "Last Name" -> comparator = Comparator.comparing(Data::getLastName);
                    case "Email Name" -> comparator = Comparator.comparing(Data::getEmail);
                    case "Image Link" -> comparator = Comparator.comparing(Data::getImageLink);
                    case "IP Address" -> comparator = Comparator.comparing(Data::getIpAddress);
                    default -> { return; }
                }
            }
            case "Descending" -> {
                switch (sortBy) {
                    case "First Name" -> comparator = (o1, o2) -> o2.getFirstName().compareTo(o1.getFirstName());
                    case "Last Name" -> comparator = (o1, o2) -> o2.getLastName().compareTo(o1.getLastName());
                    case "Email Name" -> comparator = (o1, o2) -> o2.getEmail().compareTo(o1.getEmail());
                    case "Image Link" -> comparator = (o1, o2) -> o2.getImageLink().compareTo(o1.getImageLink());
                    case "IP Address" -> comparator = (o1, o2) -> o2.getIpAddress().compareTo(o1.getIpAddress());
                    default -> {
                        return;
                    }
                }
            }
            default -> { return; }
        }
        Stream<Data> dataStream = observableData.stream().sorted(comparator);
        observableData.setAll(dataStream.toList());
    }

    public void changeData(String changeTarget, String changeTo) {
        Function<Data, Data> function;
        switch (changeTarget) {
            case "First Name" -> {
                switch (changeTo) {
                    case "Lowercase" -> function = Data::lowercaseFirstName;
                    case "Uppercase" -> function = Data::uppercaseFirstName;
                    case "Capitalize" -> function = Data::capitalizeFirstName;
                    default -> { return; }
                }
            }
            case "Last Name" -> {
                switch (changeTo) {
                    case "Lowercase" -> function = Data::lowercaseLastName;
                    case "Uppercase" -> function = Data::uppercaseLastName;
                    case "Capitalize" -> function = Data::capitalizeLastName;
                    default -> { return; }
                }
            }
            default -> { return; }
        }

        Stream<Data> dataStream = observableData.stream().map(function);
        observableData.setAll(dataStream.toList());
    }

    private void readCSV() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILENAME))) {
            while ((line = br.readLine()) != null) {
                Data newRecord = Data.getDataFromLine(line);
                data.add(newRecord);
                ipDataMap.put(newRecord.getIpKey(), newRecord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ipDataMap.forEach((key, value) -> System.out.println(key + " " + value.getFirstName() + " " + value.getLastName() + " "
        + value.getEmail() ));
    }
}
