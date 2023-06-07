package com.lab5_remastered.ClientSide;

import com.lab5_remastered.ServerSide.Room;
import com.lab5_remastered.ServerSide.Request;
import com.lab5_remastered.ServerSide.Response;
import com.lab5_remastered.ServerSide.User;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    public static Client instance;

    private User user;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private MenuController menuController;
    private ChatRoomController chatRoomController;

    public Client(User user, String host, int port) {
        try {
            this.user = user;
            this.socket = new Socket("localhost", port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Response response;
            while ((response = (Response)in.readObject()) != null) {
                switch (response.getType()) {
                    case ADD_USER_ONLINE -> {
                        if (!response.getUser().equals(user))
                            menuController.addUserOnline(response.getUser());
                    }
                    case ADD_ROOM -> menuController.addRoom(new Room(response.getText(), response.getId()));
                    case RECEIVE_MESSAGE -> chatRoomController.receiveMessage(response.getText());
                    case ENTERED_ROOM -> menuController.enterRoom(response.getId());
                    case USER_QUIT -> menuController.deleteUserOnline(response.getUser());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void shutdown() {
        Request request = new Request(Request.RequestType.QUIT_SERVER, user);
        sendRequest(request);
        try {
            in.close();
            out.close();
            if (!socket.isClosed())
                socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setChatRoomController(ChatRoomController chatRoomController) {
        this.chatRoomController = chatRoomController;
    }

    public User getUser() {
        return user;
    }

    public void sendRequest(Request request) {
        try {
            out.writeObject(request);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
