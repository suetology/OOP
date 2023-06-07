package com.lab5_remastered;

import com.lab5_remastered.ServerSide.Database;
import com.lab5_remastered.ServerSide.Server;

public class ServerApplication {
    public static void main(String[] args) {
        Database.getInstance().loadFromJSON();
        new Server(9999);
    }
}
