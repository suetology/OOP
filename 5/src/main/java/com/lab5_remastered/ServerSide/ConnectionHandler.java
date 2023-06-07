package com.lab5_remastered.ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private User user;

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final Socket socket;
    private final Server server;

    public ConnectionHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            Request request;
            while ((request = (Request)in.readObject()) != null) {
                switch (request.getType()) {
                    case REGISTER -> register(request);
                    case CREATE_ROOM -> server.createRoom(request);
                    case ADD_ROOM -> server.addRoomToUser(request);
                    case ENTER_ROOM -> server.enterRoom(request);
                    case SEND_MESSAGE -> server.broadcastMessage(request);
                    case GET_ROOM_MESSAGES -> server.broadcastRoomMessages(request);
                    case QUIT_ROOM -> server.quitRoom(request);
                    case QUIT_SERVER -> server.quitServer(request);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void shutdown() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser() {
        return user;
    }

    public void broadcast(Response response) {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void register(Request request) {
        this.user = request.getUser();
        server.registerUser(request);
    }
}
