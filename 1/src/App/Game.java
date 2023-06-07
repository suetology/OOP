package App;

import Engine.*;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Game {
    World world = new World(3, 1, 3);
    Player player = new Player(new Vector3f(24.0f, 17.0f, 24.0f));

    public void start() {
        Renderer.init();
        UIRenderer.init();

        Tree[] trees = new Tree[] {
            new Tree(new Vector3f(5.0f, 16.0f, 5.0f), 5, world),
            new Tree(new Vector3f(10.0f, 16.0f, 8.0f), 4, world),
            new Tree(new Vector3f(11.0f, 16.0f, 15.0f), 5, world),
            new Tree(new Vector3f(18.0f, 16.0f, 13.0f), 4, world),
            new Tree(new Vector3f(40.0f, 16.0f, 24.0f), 5, world),
            new Tree(new Vector3f(3.0f, 16.0f, 44.0f), 4, world),
            new Tree(new Vector3f(32.0f, 16.0f, 24.0f), 5, world),
            new Tree(new Vector3f(6.0f, 16.0f, 25.0f), 4, world),
            new Tree(new Vector3f(23.0f, 16.0f, 23.0f), 5, world),
            new Tree(new Vector3f(20.0f, 16.0f, 24.0f), 4, world),
            new Tree(new Vector3f(11.0f, 16.0f, 20.0f), 5, world),
            new Tree(new Vector3f(10.0f, 16.0f, 40.0f), 4, world),
            new Tree(new Vector3f(30.0f, 16.0f, 30.0f), 5, world),
            new Tree(new Vector3f(15.0f, 16.0f, 35.0f), 4, world),
            new Tree(new Vector3f(10.0f, 16.0f, 32.0f), 4, world),
            new Tree(new Vector3f(13.0f, 16.0f, 18.0f), 4, world),
            new Tree(new Vector3f(25.0f, 16.0f, 41.0f), 5, world),
            new Tree(new Vector3f(44.0f, 16.0f, 4.0f), 4, world),
            new Tree(new Vector3f(20.0f, 16.0f, 35.0f), 5, world),
            new Tree(new Vector3f(25.0f, 16.0f, 7.0f), 4, world),
            new Tree(new Vector3f(41, 16.0f, 41), 5, world),
            new Tree(new Vector3f(37, 16.0f, 38), 4, world),
            new Tree(new Vector3f(36, 16.0f, 4), 5, world),
            new Tree(new Vector3f(29, 16.0f, 14), 4, world),
            new Tree(new Vector3f(33, 16.0f, 8), 5, world),
            new Tree(new Vector3f(44, 16.0f, 15), 4, world)
        };
    }

    public void update() {
        player.Rotate();
        player.Move();

        if (Events.getMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            player.destroyBlock(world);
        }
        if (Events.getMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            player.placeBlock(world);
        }
        if (Events.getMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)) {
            player.changeBlockToPlace(world);
        }

        Renderer.render(world, player.getView());
        UIRenderer.renderCrosshair();
    }

    public void end() {
        Renderer.terminate();
        UIRenderer.terminate();
    }
}
