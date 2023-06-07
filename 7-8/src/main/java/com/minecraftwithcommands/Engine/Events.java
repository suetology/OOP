package com.minecraftwithcommands.Engine;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class Events {
    private static final int BUTTONS = 1032;
    private static final int KEYS = 1024;
    private static boolean[] buttons;
    private static long[] frames;
    private static long currentFrame;
    private static Vector2f mousePosition;
    private static Vector2f mouseDelta;

    public static void init() {
        if (!WindowManager.isInitialized()) {
            System.err.println("Window is not initialized");
        }
        currentFrame = 0;
        mousePosition = new Vector2f(0.0f, 0.0f);
        mouseDelta = new Vector2f(0.0f, 0.0f);

        buttons = new boolean[BUTTONS];
        frames = new long[BUTTONS];

        GLFW.glfwSetKeyCallback(WindowManager.getWindow(), (window, key, scancode, action, mods)->{
            if (action == GLFW.GLFW_PRESS) {
                buttons[key] = true;
                frames[key] = currentFrame;
            } else if (action == GLFW.GLFW_RELEASE) {
                buttons[key] = false;
                frames[key] = currentFrame;
            }
        });

        GLFW.glfwSetMouseButtonCallback(WindowManager.getWindow(), (window, button, action, mods)->{
           if (action == GLFW.GLFW_PRESS) {
               buttons[KEYS + button] = true;
               frames[KEYS + button] = currentFrame;
           } else if (action == GLFW.GLFW_RELEASE) {
               buttons[KEYS + button] = false;
               frames[KEYS + button] = currentFrame;
           }
        });

        GLFW.glfwSetCursorPosCallback(WindowManager.getWindow(), (window, xPos, yPos)->{
            float x = (float)xPos;
            float y = (float)yPos;
            mouseDelta.x = x - mousePosition.x;
            mouseDelta.y = mousePosition.y - y;

            mousePosition = new Vector2f(x, y);
        });
    }

    public static void onUpdate() {
        mouseDelta = new Vector2f(0.0f, 0.0f);
        currentFrame++;
    }

    public static Vector2f getMousePosition() {
        return mousePosition;
    }

    public static Vector2f getMouseDelta() {
        return mouseDelta;
    }

    public static boolean getKey(int key) {
        return buttons[key];
    }

    public static boolean getKeyDown(int key) {
        return buttons[key] && frames[key] == currentFrame;
    }

    public static boolean getKeyUp(int key) {
        return !buttons[key] && frames[key] == currentFrame;
    }

    public static boolean getMouseButton(int button) {
        return buttons[KEYS + button];
    }

    public static boolean getMouseButtonDown(int button) {
        return buttons[KEYS + button] && frames[KEYS + button] == currentFrame;
    }

    public static boolean getMouseButtonUp(int button) {
        return !buttons[KEYS + button] && frames[KEYS + button] == currentFrame;
    }
}
