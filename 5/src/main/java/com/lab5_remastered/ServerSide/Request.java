package com.lab5_remastered.ServerSide;

public class Request extends DispatchableData {
    public enum RequestType {
        REGISTER,
        CREATE_ROOM,
        ADD_ROOM,
        ENTER_ROOM,
        SEND_MESSAGE,
        GET_ROOM_MESSAGES,
        QUIT_ROOM,
        QUIT_SERVER
    }
    private final RequestType type;

    public Request(RequestType type, User owner, String text, String roomId) {
        this.type = type;
        this.text = text;
        this.id = roomId;
        this.user = owner;
    }

    public Request(RequestType type, User user) {
        this.type = type;
        this.user = user;
    }

    public Request(RequestType type, User user, String roomId) {
        this.type = type;
        this.user = user;
        this.id = roomId;
    }

    public RequestType getType() {
        return type;
    }
}
