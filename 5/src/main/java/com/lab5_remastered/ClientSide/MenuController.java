package com.lab5_remastered.ClientSide;

import com.lab5_remastered.ServerSide.Request;
import com.lab5_remastered.ServerSide.Room;
import com.lab5_remastered.ServerSide.User;
import com.lab5_remastered.Utils.ActionButtonTableCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML private TableView<Room> roomsTable;
    @FXML private TableView<User> usersTable;
    @FXML private TextField roomIdField;

    //ObservableList<Room> roomsList = FXCollections.observableArrayList();

    private Stage roomCreatorStage;
    private Stage chatRoomStage;
    private ChatRoomController chatRoomController;

    public void setRoomCreatorStage(Stage stage) {
        roomCreatorStage = stage;
    }
    public void setChatRoomStage(Stage stage) { chatRoomStage = stage; }
    public void setChatRoomController(ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initRoomsTable();
        initUsersTable();
    }

    private void initRoomsTable() {
        TableColumn<Room, String> roomNameColumn = new TableColumn<>("Room name");
        roomNameColumn.setPrefWidth(167);
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Room, String> roomIdColumn = new TableColumn<>("Room ID");
        roomIdColumn.setPrefWidth(167);
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Room, Button> enterRoomColumn = new TableColumn<>();
        enterRoomColumn.setPrefWidth(164);
        enterRoomColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Enter room", this::onEnterRoomButtonPressed));

        roomsTable.getColumns().addAll(roomNameColumn, roomIdColumn, enterRoomColumn);
    }

    private void initUsersTable() {
        TableColumn<User, String> usernameColumn = new TableColumn<>("Users online");
        usernameColumn.setPrefWidth(150);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, Button> enterChatColumn = new TableColumn<>();
        enterChatColumn.setPrefWidth(150);
        enterChatColumn.setCellFactory(ActionButtonTableCell.forTableColumn("Enter chat", this::onEnterChatButtonPressed));

        usersTable.getColumns().addAll(usernameColumn, enterChatColumn);
    }

    public void addRoom(Room room) {
        roomsTable.getItems().add(room);
        roomsTable.refresh();
    }

    public void addUserOnline(User user) {
        usersTable.getItems().add(user);
        usersTable.refresh();
    }

    public void deleteUserOnline(User user) {
        usersTable.getItems().remove(user);
        usersTable.refresh();
    }

    public void enterRoom(String roomId) {
        Platform.runLater(() -> {
            //for (Room room : roomsTable.getItems())
                //if (roomId.equals(room.getId())) {
                    chatRoomController.setRoom(roomId);
                    chatRoomStage.show();
                    return;
                //}
        });
    }

    @FXML
    private void onCreateRoomButtonPressed() {
        roomCreatorStage.show();
    }

    @FXML
    private void onJoinRoomButtonPressed() {
        String roomId = roomIdField.getText();
        if (roomId.isBlank())
            return;

        Request request = new Request(Request.RequestType.ADD_ROOM, Client.instance.getUser(), roomId);
        Client.instance.sendRequest(request);
    }


    private void onEnterRoomButtonPressed(Room room) {
        Request request = new Request(Request.RequestType.ENTER_ROOM, Client.instance.getUser(), room.getId());
        Client.instance.sendRequest(request);
    }

    private void onEnterChatButtonPressed(User user) {
        String roomId;
        if (user.getUsername().compareTo(Client.instance.getUser().getUsername()) > 0)
            roomId = user.getUsername() + Client.instance.getUser().getUsername();
        else
            roomId = Client.instance.getUser().getUsername() + user.getUsername();

        Request request = new Request(Request.RequestType.ENTER_ROOM, Client.instance.getUser(), roomId);
        Client.instance.sendRequest(request);
    }
}