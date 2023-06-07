package com.minecraftwithcommands.Engine;

import com.minecraftwithcommands.App.Game;
import com.minecraftwithcommands.CommandLineApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EntryPoint {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread commandLineThread = new Thread(CommandLineApplication::start);
        executorService.submit(commandLineThread);

        WindowManager.init("Minecraft", 1080, 720, false);
        Events.init();

        Game game = new Game();
        game.start();

        while (WindowManager.windowOpened()) {
            WindowManager.clearWindow();

            game.update();

            Events.onUpdate();
            WindowManager.onWindowUpdate();
        }
        game.end();
        executorService.shutdown();
    }
}
