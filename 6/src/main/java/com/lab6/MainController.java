package com.lab6;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {
    private final int THREAD_COUNT = 10;
    private final String DATA_FILENAME = "MOCK_DATA.csv";
    private final String OUTPUT_FOLDER_NAME = "data/";
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @FXML private TableView<User> dataTable;
    @FXML private TableView<User> infoTable;

    private List<User> users;
    private List<User> info;
    private boolean isRunning = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<User, String> imageLinkColumn = new TableColumn<>("Image Link");
        imageLinkColumn.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        TableColumn<User, String> ipAddressColumn = new TableColumn<>("IP Address");
        ipAddressColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));

        dataTable.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, imageLinkColumn, ipAddressColumn);
        users = Collections.synchronizedList(dataTable.getItems());

        TableColumn<User, String> infoColumn = new TableColumn<>("Info");
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("filename"));
        TableColumn<User, Button> deleteColumn = new TableColumn<>();
        deleteColumn.setCellValueFactory(data -> {
            Button button = new Button("Delete");
            button.setOnAction(e -> {
                onDeleteButtonPressed(data.getValue());
            });
            return new SimpleObjectProperty<>(button);
        });
        infoTable.getColumns().addAll(infoColumn, deleteColumn);
        info = Collections.synchronizedList(infoTable.getItems());

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILENAME))) {
            while ((line = br.readLine()) != null)
                users.add(User.getUserFromLine(line));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        isRunning = false;
        executor.shutdown();
    }

    private void onDeleteButtonPressed(User user) {
        File file = new File(OUTPUT_FOLDER_NAME + user.getFilename());
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                synchronized (users) {
                    users.add(User.getUserFromLine(line));
                }
                synchronized (info) {
                    info.remove(user);
                }
            } catch (IOException e) {
                System.err.println("onDeleteButtonPressed: " + e.getMessage());
            }
        }
        file.delete();
    }

    @FXML
    private void onClearButtonPressed() {
        synchronized (info) {
            while (info.size() > 0)
                onDeleteButtonPressed(info.get(0));
        }
    }

    @FXML
    private void onRunButtonPressed() {
        if (isRunning)
            return;

        isRunning = true;
        Runnable run = () -> {
            while (isRunning) {
                synchronized (users) {
                    if (users.size() > 0)
                        removeFromList();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        for (int i = 0; i < THREAD_COUNT; i++)
            executor.execute(run);
    }

    private void removeFromList() {
        User user;
        synchronized (users) {
            user = users.remove(0);
        }
        try (FileWriter fw = new FileWriter(OUTPUT_FOLDER_NAME + user.getFilename())) {
            fw.write(user.getFirstName() + ";" + user.getLastName() + ";" + user.getEmail() + ";" + user.getImageLink() + ";" + user.getIpAddress());
        } catch (IOException e) {
            System.err.println("removeFromList: " + e.getMessage());
        }
        synchronized (info) {
            info.add(user);
        }
    }
}