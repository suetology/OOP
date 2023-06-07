package com.lab5_remastered;

import com.lab5_remastered.ClientSide.ChatRoomController;
import com.lab5_remastered.ClientSide.Client;
import com.lab5_remastered.ClientSide.MenuController;
import com.lab5_remastered.ClientSide.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {
    @Override
    public void start(Stage menuStage) throws IOException {
        FXMLLoader registrationLoader = new FXMLLoader(ClientApplication.class.getResource("registration-view.fxml"));
        Scene registrationScene = new Scene(registrationLoader.load());
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Messenger");
        registrationStage.setScene(registrationScene);
        registrationStage.show();

        FXMLLoader menuLoader = new FXMLLoader(ClientApplication.class.getResource("menu-view.fxml"));
        Scene menuScene = new Scene(menuLoader.load());
        menuStage.setTitle("Messenger");
        menuStage.setScene(menuScene);

        FXMLLoader roomCreatorLoader = new FXMLLoader(ClientApplication.class.getResource("roomcreator-view.fxml"));
        Scene roomCreatorScene = new Scene(roomCreatorLoader.load());
        Stage roomCreatorStage = new Stage();
        roomCreatorStage.setTitle("Create room");
        roomCreatorStage.setScene(roomCreatorScene);

        FXMLLoader chatroomLoader = new FXMLLoader(ClientApplication.class.getResource("chatroom-view.fxml"));
        Scene chatroomScene = new Scene(chatroomLoader.load());
        Stage chatroomStage = new Stage();
        chatroomStage.setTitle("Messenger");
        chatroomStage.setScene(chatroomScene);

        ChatRoomController chatRoomController = chatroomLoader.getController();

        MenuController menuController = menuLoader.getController();
        menuController.setRoomCreatorStage(roomCreatorStage);
        menuController.setChatRoomStage(chatroomStage);
        menuController.setChatRoomController(chatRoomController);
        RegistrationController registrationController = registrationLoader.getController();
        registrationController.setMenu(menuStage, menuController);
        registrationController.setChatRoomController(chatRoomController);

        menuStage.setOnHiding(e -> Client.instance.shutdown());
        chatroomStage.setOnHiding(e -> chatRoomController.quitRoom());
    }

    public static void main(String[] args) {
        launch();
    }
}