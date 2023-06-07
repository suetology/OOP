package com.lab5_remastered.ClientSide;

import com.lab5_remastered.ServerSide.Request;
import com.lab5_remastered.ServerSide.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RegistrationController {
    @FXML private TextField usernameField;

    private Stage menuStage;
    private MenuController menuController;
    private ChatRoomController chatRoomController;

    public void setMenu(Stage menuStage, MenuController menuController) {
        this.menuStage = menuStage;
        this.menuController = menuController;
    }

    public void setChatRoomController(ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
    }

    @FXML
    private void onRegisterButtonPressed() {
        String username = usernameField.getText();
        if (username.isBlank())
            return;

        User user = new User(username);
        Client client = new Client(user, "127.0.0.1", 9999);
        client.setMenuController(menuController);
        client.setChatRoomController(chatRoomController);
        //menuStage.setUserData(client);
        Client.instance = client;

        Request request = new Request(Request.RequestType.REGISTER, user);
        client.sendRequest(request);

        new Thread(client).start();

        usernameField.getScene().getWindow().hide();
        menuStage.show();
    }
}
