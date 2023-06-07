package com.lab5_remastered.ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server implements Closeable {
    private Socket clientSocket;
    private ServerSocket serverSocket;

    private final ArrayList<ConnectionHandler> connections = new ArrayList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Thread acceptClients = new Thread(() -> {
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                ConnectionHandler client = new ConnectionHandler(clientSocket, this);
                connections.add(client);
                new Thread(client).start();
            }
        });
        acceptClients.start();
    }

    @Override
    public void close() {
        shutdown();
    }

    private void shutdown() {
        for (ConnectionHandler ch : connections)
            ch.shutdown();

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createRoom(Request request) {
        Room room = new Room(request.getText(), request.getId());
        Database.getInstance().getRooms().add(room);
        Database.getInstance().saveToJSON();

        Response response = new Response(Response.ResponseType.ADD_ROOM, request.getText(), request.getId());
        broadcastToUser(request.getUser(), response);
    }

    public void addRoomToUser(Request request) {
        for (Room room : Database.getInstance().getRooms()) {
            if (room.getId().equals(request.getId())) {
                Response response = new Response(Response.ResponseType.ADD_ROOM, room.getName(), room.getId());
                broadcastToUser(request.getUser(), response);
            }
        }
    }

    public void registerUser(Request request) {
        for (User user : Database.getInstance().getUsers()) {
            String roomName = "Private Room";
            String roomId;
            if (user.getUsername().compareTo(request.getUser().getUsername()) > 0)
                roomId = user.getUsername() + request.getUser().getUsername();
            else
                roomId = request.getUser().getUsername() + user.getUsername();
            Room room = new Room(roomName, roomId);
            Database.getInstance().getRooms().add(room);
        }
        Database.getInstance().saveToJSON();

        Response response = new Response(Response.ResponseType.ADD_USER_ONLINE, request.getUser());
        broadcastToAllUsers(response);

        for (User user : Database.getInstance().getUsers()) {
            response = new Response(Response.ResponseType.ADD_USER_ONLINE, user);
            broadcastToUser(request.getUser(), response);
        }
        Database.getInstance().getUsers().add(request.getUser());
    }

    public void enterRoom(Request request) {
        for (Room room : Database.getInstance().getRooms())
            if (room.getId().equals(request.getId())) {
                room.addUser(request.getUser());
                break;
            }

        Response response = new Response(Response.ResponseType.ENTERED_ROOM, request.getUser(), request.getId());
        broadcastToUser(request.getUser(), response);
    }

    public void quitServer(Request request) {
        Response response = new Response(Response.ResponseType.USER_QUIT, request.getUser());
        broadcastToAllUsers(response);
    }

    public void quitRoom(Request request) {
        for (Room room : Database.getInstance().getRooms())
            if (room.getId().equals(request.getId()))
                room.getUsers().remove(request.getUser());
    }

    public void broadcastMessage(Request request) {
        Date date = new Date(System.currentTimeMillis());
        String dateFormatted = formatter.format(date);
        String message = dateFormatted + " " + request.getUser().getUsername() + ": " + request.getText() + "\n";

        for (Room room : Database.getInstance().getRooms())
            if (room.getId().equals(request.getId()))
                room.addMessage(message);
        Database.getInstance().saveToJSON();

        Response response = new Response(Response.ResponseType.RECEIVE_MESSAGE, message);
        broadcastToRoom(request.getId(), response);
    }

    public void broadcastRoomMessages(Request request) {
        StringBuilder messagesConnected = new StringBuilder();
        for (Room room : Database.getInstance().getRooms())
            if (room.getId().equals(request.getId())) {
                for (String message : room.getMessages())
                    messagesConnected.append(message);
                break;
            }

        Response response = new Response(Response.ResponseType.RECEIVE_MESSAGE, messagesConnected.toString());
        broadcastToUser(request.getUser(), response);
    }

    public void broadcastToUser(User user, Response response) {
        for (ConnectionHandler ch : connections) {
            if (ch.getUser() != null && user.equals(ch.getUser())) {
                ch.broadcast(response);
                break;
            }
        }
    }

    public void broadcastToAllUsers(Response response) {
        for (ConnectionHandler ch : connections)
            if (ch.getUser() != null)
                ch.broadcast(response);
    }

    public void broadcastToRoom(String roomId, Response response) {
        ArrayList<Room> rooms = Database.getInstance().getRooms();
        for (Room room : rooms) {
            if (room.getId().equals(roomId)) {
                //room.addMessage(response.getText());
                for (User user : room.getUsers())
                    broadcastToUser(user, response);
                break;
            }
        }
    }
}
