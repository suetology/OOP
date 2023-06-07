package Engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class WindowManager {
    private static boolean initialized = false;
    private static long window;
    private static int width;
    private static int height;
    private static float aspectRatio;
    private static String title;

    public static void init(String title, int width, int height, boolean fullscreen) {
        if (initialized) {
            System.err.println("Window has been already initialized");
            return;
        }
        WindowManager.title = title;
        WindowManager.width = width;
        WindowManager.height = height;
        aspectRatio = (float)width / height;

        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        long monitor;
        if (fullscreen)
            monitor = GLFW.glfwGetPrimaryMonitor();
        else
            monitor = MemoryUtil.NULL;

        window = GLFW.glfwCreateWindow(width, height, title, monitor, MemoryUtil.NULL);
        GLFW.glfwSetInputMode(WindowManager.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glEnable(GL11.GL_BLEND );
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        initialized = true;
    }

    public static long getWindow() {
        return window;
    }
    public static float getAspectRatio() { return aspectRatio; }
    public static boolean isInitialized() { return initialized; }

    public static boolean windowOpened() {
        if (Events.getKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            GLFW.glfwSetWindowShouldClose(window, true);
        }
        return !GLFW.glfwWindowShouldClose(window);
    }

    public static void clearWindow() {
        GL11.glClearColor(0.15f, 0.6f, 0.9f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void onWindowUpdate() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public static void closeWindow()
    {
        if (!GLFW.glfwWindowShouldClose(window))
        {
            GLFW.glfwSetWindowShouldClose(window, true);
        }
        GLFW.glfwTerminate();
    }
}
