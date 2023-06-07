package com.lab5_remastered.ServerSide;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.username, ((User)obj).username);
    }
}
