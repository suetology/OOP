package com.lab5_remastered.ServerSide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Database {
    private static Database instance;
    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Room> rooms = new ArrayList<>();

    private JSONObject root;

    private Database() {}

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void loadFromJSON() {
        try {
            InputStream is = new FileInputStream("data.json");
            JSONTokener tokener = new JSONTokener(is);
            root = new JSONObject(tokener);

            JSONArray roomsArray = root.getJSONArray("rooms");
            for (int i = 0; i < roomsArray.length(); i++) {
                Room room = readRoomObject(roomsArray.getJSONObject(i));
                rooms.add(room);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToJSON() {
        root = new JSONObject();

        JSONArray roomsArray = new JSONArray();
        for (Room room : rooms) {
            JSONObject roomObject = writeRoomObject(room);
            roomsArray.put(roomObject);
        }
        root.put("rooms", roomsArray);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("data.json");
            fileWriter.write(root.toString());
        } catch(IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private JSONObject writeRoomObject(Room room) {
        JSONObject roomObject = new JSONObject();
        roomObject.put("name", room.getName());
        roomObject.put("id", room.getId());

        JSONArray messageArray = new JSONArray();
        for (String message : room.getMessages())
            messageArray.put(message);

        roomObject.put("messages", messageArray);
        return roomObject;
    }

    private Room readRoomObject(JSONObject roomObject) {
        String name = (String)roomObject.get("name");
        String id = (String)roomObject.get("id");
        Room room = new Room(name, id);

        JSONArray messageArray = roomObject.getJSONArray("messages");
        for (int i = 0; i < messageArray.length(); i++) {
            String message = (String)messageArray.get(i);
            room.addMessage(message);
        }
        return room;
    }
}
