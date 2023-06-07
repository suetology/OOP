package com.lab5_remastered.ServerSide;

public class Response extends DispatchableData {
    public enum ResponseType {
        ERROR,
        ADD_USER_ONLINE,
        GET_USERS_ONLINE,
        ADD_ROOM,
        ENTERED_ROOM,
        RECEIVE_MESSAGE,
        USER_QUIT
    }
    private final ResponseType type;

    public Response(ResponseType type, String text, String id) {
        this.type = type;
        this.text = text;
        this.id = id;
    }

    public Response(ResponseType type, String text) {
        this.type = type;
        this.text = text;
    }

    public Response(ResponseType type, User user) {
        this.type = type;
        this.user = user;
    }

    public Response(ResponseType type, User user, String id) {
        this.type = type;
        this.user = user;
        this.id = id;
    }

    public ResponseType getType() {
        return type;
    }
}
