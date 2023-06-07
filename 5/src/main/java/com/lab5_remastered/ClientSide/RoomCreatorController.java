package com.lab5_remastered.ClientSide;

import com.lab5_remastered.ServerSide.Request;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RoomCreatorController {
    @FXML private TextField roomNameField;
    @FXML private TextField roomIdField;

    @FXML
    private void onCreateRoomButtonPressed() {
        if (roomIdField.getText().isEmpty() || roomNameField.getText().isEmpty())
            return;

        Client client = Client.instance;
        Request createRoomRequest = new Request(Request.RequestType.CREATE_ROOM, client.getUser(),
                                                            roomNameField.getText(), roomIdField.getText());
        client.sendRequest(createRoomRequest);

        roomNameField.clear();
        roomIdField.clear();
        roomNameField.getScene().getWindow().hide();
    }
}
