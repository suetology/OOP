package com.lab5_remastered.ServerSide;

import java.io.Serializable;

public class DispatchableData implements Serializable {
    protected User user;
    protected String text;
    protected String id;

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
