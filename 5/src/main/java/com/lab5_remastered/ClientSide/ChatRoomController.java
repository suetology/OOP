package com.lab5_remastered.ClientSide;

import com.lab5_remastered.ServerSide.Request;
import com.lab5_remastered.ServerSide.Room;
import com.lab5_remastered.ServerSide.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable {
    @FXML private TextArea chatTextArea;
    @FXML private TextArea messageTextArea;

    private String roomId;

    public void setRoom(String roomId) {
        this.roomId = roomId;

        chatTextArea.clear();

        Request request = new Request(Request.RequestType.GET_ROOM_MESSAGES, Client.instance.getUser(), roomId);
        Client.instance.sendRequest(request);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatTextArea.setEditable(false);

        TableColumn<User, String> usernameColumn = new TableColumn<>("Users");
        usernameColumn.setPrefWidth(150);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    @FXML
    private void onSendButtonPressed() {
        String message = messageTextArea.getText();
        if (message.isBlank())
            return;

        Request request = new Request(Request.RequestType.SEND_MESSAGE, Client.instance.getUser(), message, roomId);
        Client.instance.sendRequest(request);

        messageTextArea.clear();
    }

    public void receiveMessage(String message) {
        chatTextArea.appendText(message);
    }

    public void quitRoom() {
        Request request = new Request(Request.RequestType.QUIT_ROOM, Client.instance.getUser(), roomId);
        Client.instance.sendRequest(request);
    }
}
