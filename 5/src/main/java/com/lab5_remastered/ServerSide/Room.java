package com.lab5_remastered.ServerSide;

import java.util.ArrayList;

public class Room {
    public enum RoomType {
        PRIVATE,
        GROUP
    }
    private final String name;
    private final String id;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<String> messages = new ArrayList<>();

    public Room(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
