package com.minecraftwithcommands.App;

import com.minecraftwithcommands.Engine.Shader;
import com.minecraftwithcommands.Engine.VertexArray;
import com.minecraftwithcommands.Engine.VertexBuffer;
import com.minecraftwithcommands.Engine.WindowManager;
import org.lwjgl.opengl.GL15;

public class UIRenderer {
    private static float[] crosshairVertices = {
        -0.02f,  0.0f, 0.0f,
         0.02f,  0.0f, 0.0f,
         0.0f, -0.02f, 0.0f,
         0.0f,  0.02f, 0.0f
    };

    private static VertexArray crosshairVAO = new VertexArray();
    private static VertexBuffer crosshairVBO = new VertexBuffer();

    private static Shader crosshairShader = new Shader("res/cross.vertex", "res/cross.fragment");

    public static void init() {
        crosshairVAO.bind();
        crosshairVBO.bufferData(crosshairVertices, 1);
        crosshairVAO.unbind();
    }

    public static void renderCrosshair() {
        crosshairVAO.bind();
        crosshairVBO.bind();
        crosshairShader.bind();
        crosshairShader.setUniform("uAspectRatio", WindowManager.getAspectRatio());
        GL15.glDrawArrays(GL15.GL_LINES, 0, 4);
        crosshairVAO.unbind();
        crosshairVBO.unbind();
        crosshairShader.unbind();
    }

    public static void terminate() {
        crosshairShader.delete();
        crosshairVBO.delete();
        crosshairVAO.delete();
    }
}
