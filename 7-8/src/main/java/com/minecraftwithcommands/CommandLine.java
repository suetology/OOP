package com.minecraftwithcommands;

import java.util.concurrent.LinkedBlockingQueue;

public class CommandLine {
    private static CommandLine instance;
    public static CommandLine getInstance() {
        if (instance == null)
            instance = new CommandLine();
        return instance;
    }

    private CommandLine() { }

    public enum CommandType {
        ERROR,
        MOVE_FORWARD,
        MOVE_BACK,
        MOVE_UP,
        MOVE_DOWN,
        MOVE_RIGHT,
        MOVE_LEFT,
        ROTATE_UP,
        ROTATE_DOWN,
        ROTATE_RIGHT,
        ROTATE_LEFT,
        DESTROY_BLOCK,
        PLACE_BLOCK,
        CHANGE_BLOCK
    }

    private final LinkedBlockingQueue<CommandType> commandsQueue = new LinkedBlockingQueue<>();

    public void proceedCommand(String commandText) throws InterruptedException {
        String[] command = commandText.split("[()]");
        for (int i = 0; i < command.length; i++)
            command[i] = command[i].replaceAll("\\s", "");  

        try {
            if (!command[0].equalsIgnoreCase("repeat")) {
                executeCommand(command);
            } else {
                String[] actualCommand = { command[1], command[2] };
                int repeatCount = Integer.parseInt(command[3].replaceAll(",", ""));
                for (int i = 0; i < repeatCount; i++)
                    executeCommand(actualCommand);
            }
        } catch (RuntimeException e) {
            System.err.println("Wrong command");
        }
    }

    public void executeCommand(String[] command) throws InterruptedException {
        switch (command[0].toLowerCase()) {
            case "move" -> {
                switch (command[1].toLowerCase()) {
                    case "forward" -> commandsQueue.put(CommandType.MOVE_FORWARD);
                    case "back" -> commandsQueue.put(CommandType.MOVE_BACK);
                    case "up" -> commandsQueue.put(CommandType.MOVE_UP);
                    case "down" -> commandsQueue.put(CommandType.MOVE_DOWN);
                    case "right" -> commandsQueue.put(CommandType.MOVE_RIGHT);
                    case "left" -> commandsQueue.put(CommandType.MOVE_LEFT);
                }
            }
            case "rotate" -> {
                switch (command[1].toLowerCase()) {
                    case "up" -> commandsQueue.put(CommandType.ROTATE_UP);
                    case "down" -> commandsQueue.put(CommandType.ROTATE_DOWN);
                    case "right" -> commandsQueue.put(CommandType.ROTATE_RIGHT);
                    case "left" -> commandsQueue.put(CommandType.ROTATE_LEFT);
                }
            }
            case "destroyblock" -> commandsQueue.put(CommandType.DESTROY_BLOCK);
            case "placeblock" -> commandsQueue.put(CommandType.PLACE_BLOCK);
            case "changeblock" -> commandsQueue.put(CommandType.CHANGE_BLOCK);
        }
    }

    public CommandType getLastCommand() {
        try {
            return commandsQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
