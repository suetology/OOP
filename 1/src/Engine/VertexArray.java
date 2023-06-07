package Engine;

import org.lwjgl.opengl.GL30;

public class VertexArray {
    private int id;

    public VertexArray() {
        id = GL30.glGenVertexArrays();
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void delete() {
        GL30.glDeleteVertexArrays(id);
    }
}
