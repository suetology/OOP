package com.lab3;

public class Connection {
    public enum Type {
        USER_DATA,
        SINGLETON,
        CONTROLLER
    }

    private final Type type = Type.USER_DATA;

    private static Connection instance;
    public static Connection getInstance() {
        if (instance == null)
            instance = new Connection();
        return instance;
    }

    public Type getType() {
        return type;
    }
}
