package com.minecraftwithcommands.Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private float fov;
    private float yaw;
    private float pitch;
    private float rotationSensitivity;
    private float moveSpeed;
    private boolean isRotating;

    public Camera() {
        Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
        init(position);
    }

    public Camera(Vector3f position) {
        init(position);
    }

    public Vector3f getDirection() { return front; }
    public Vector3f getPosition() { return position; }
    public void setPosition(Vector3f newPosition) { position = newPosition; }

    private void init(Vector3f position) {
        this.position = position;
        up = new Vector3f(0.0f, 1.0f, 0.0f);
        front = new Vector3f(0.0f, 0.0f, -1.0f);
        fov = 45.0f;
        yaw = -90.0f;
        pitch = 0.0f;
        moveSpeed = 1.0f;
        rotationSensitivity = 6.0f;
        isRotating = true;
    }

    public void Move() {
        int direction = 0;

        if (Events.getKey(GLFW.GLFW_KEY_W)) {
            direction += Direction.FORWARD.id;
        }
        if (Events.getKey(GLFW.GLFW_KEY_S)) {
            direction += Direction.BACK.id;
        }
        if (Events.getKey(GLFW.GLFW_KEY_A)) {
            direction += Direction.LEFT.id;
        }
        if (Events.getKey(GLFW.GLFW_KEY_D)) {
            direction += Direction.RIGHT.id;
        }
        if (Events.getKey(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            direction += Direction.DOWN.id;
        }
        if (Events.getKey(GLFW.GLFW_KEY_SPACE)) {
            direction += Direction.UP.id;
        }
        if ((direction & Direction.UP.id) > 0) {
            position.y += moveSpeed * 0.05f;
        }
        if ((direction & Direction.DOWN.id) > 0) {
            position.y -= moveSpeed * 0.05f;
        }
        if ((direction & Direction.FORWARD.id) > 0) {
            position.x += moveSpeed * front.x * 0.05f;
            position.y += moveSpeed * front.y * 0.05f;
            position.z += moveSpeed * front.z * 0.05f;
        }
        if ((direction & Direction.BACK.id) > 0) {
            position.x -= moveSpeed * front.x * 0.05f;
            position.y -= moveSpeed * front.y * 0.05f;
            position.z -= moveSpeed * front.z * 0.05f;
        }
        if ((direction & Direction.RIGHT.id) > 0) {
            Vector3f f = new Vector3f(front);
            Vector3f right = f.cross(new Vector3f(0.0f, 1.0f, 0.0f)).normalize();
            position.x += moveSpeed * right.x * 0.05f;
            position.y += moveSpeed * right.y * 0.05f;
            position.z += moveSpeed * right.z * 0.05f;
        }
        if ((direction & Direction.LEFT.id) > 0) {
            Vector3f f = new Vector3f(front);
            Vector3f right = f.cross(new Vector3f(0.0f, 1.0f, 0.0f)).normalize();
            position.x -= moveSpeed * right.x * 0.05f;
            position.y -= moveSpeed * right.y * 0.05f;
            position.z -= moveSpeed * right.z * 0.05f;
        }
    }

    public void Move(Direction direction) {
        switch (direction) {
            case UP -> position.y += moveSpeed * 0.2f;
            case DOWN -> position.y -= moveSpeed * 0.2f;
            case FORWARD -> {
                position.x += moveSpeed * front.x * 0.2f;
                position.y += moveSpeed * front.y * 0.2f;
                position.z += moveSpeed * front.z * 0.2f;
            }
            case BACK -> {
                position.x -= moveSpeed * front.x * 0.2f;
                position.y -= moveSpeed * front.y * 0.2f;
                position.z -= moveSpeed * front.z * 0.2f;
            }
            case RIGHT -> {
                Vector3f f = new Vector3f(front);
                Vector3f right = f.cross(new Vector3f(0.0f, 1.0f, 0.0f)).normalize();
                position.x += moveSpeed * right.x * 0.2f;
                position.y += moveSpeed * right.y * 0.2f;
                position.z += moveSpeed * right.z * 0.2f;
            }
            case LEFT -> {
                Vector3f f = new Vector3f(front);
                Vector3f right = f.cross(new Vector3f(0.0f, 1.0f, 0.0f)).normalize();
                position.x -= moveSpeed * right.x * 0.2f;
                position.y -= moveSpeed * right.y * 0.2f;
                position.z -= moveSpeed * right.z * 0.2f;
            }
        }
    }

    public void Rotate() {
        if (!isRotating) {
            return;
        }
        yaw += rotationSensitivity * Events.getMouseDelta().x * 0.1f;
        pitch += rotationSensitivity * Events.getMouseDelta().y * 0.1f;

        if (pitch > 89.0f) {
            pitch = 89.0f;
        } else if (pitch < -89.0f) {
            pitch = -89.0f;
        }
        Vector3f newFront = new Vector3f();
        newFront.x = (float)(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        newFront.y = (float)Math.sin(Math.toRadians(pitch));
        newFront.z = (float)(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front = newFront.normalize();

        Vector3f f = new Vector3f(front);
        Vector3f right = f.cross(new Vector3f(0.0f, 1.0f, 0.0f));
        up = right.cross(front);
    }

    public void Rotate(Direction direction) {
        if (direction == Direction.FORWARD || direction == Direction.BACK)
            return;

        switch (direction) {
            case UP -> pitch += rotationSensitivity * 0.3f;
            case DOWN -> pitch -= rotationSensitivity * 0.3f;
            case RIGHT -> yaw += rotationSensitivity * 0.3f;
            case LEFT -> yaw -= rotationSensitivity * 0.3f;
        }

        if (pitch > 89.0f) {
            pitch = 89.0f;
        } else if (pitch < -89.0f) {
            pitch = -89.0f;
        }
        Vector3f newFront = new Vector3f();
        newFront.x = (float)(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        newFront.y = (float)Math.sin(Math.toRadians(pitch));
        newFront.z = (float)(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front = newFront.normalize();

        Vector3f f = new Vector3f(front);
        Vector3f right = f.cross(new Vector3f(0.0f, 1.0f, 0.0f));
        up = right.cross(front);
    }

    public Matrix4f getLookAtMatrix() {
        Matrix4f lookAtMatrix = new Matrix4f();
        Vector3f center = new Vector3f();
        center.x = position.x + front.x;
        center.y = position.y + front.y;
        center.z = position.z + front.z;
        lookAtMatrix = lookAtMatrix.setLookAt(position, center, up);
        return lookAtMatrix;
    }

    public Matrix4f getPerspectiveMatrix() {
        Matrix4f perspective = new Matrix4f();
        perspective = perspective.setPerspective(fov, WindowManager.getAspectRatio(), 0.1f, 100.0f);
        return perspective;
    }
}
