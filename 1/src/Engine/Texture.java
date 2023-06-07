package Engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {
    private int id;
    private int width;
    private int height;
    private int textureNumber;
    private static int textureCount = 0;

    public Texture(String filename) {
        id = GL11.glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + textureCount);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        textureNumber = textureCount;
        textureCount++;

        ByteBuffer buffer;
        MemoryStack stack = MemoryStack.stackPush();
        IntBuffer w = stack.mallocInt(1);
        IntBuffer h = stack.mallocInt(1);
        IntBuffer c = stack.mallocInt(1);

        STBImage.stbi_set_flip_vertically_on_load(true);
        buffer = STBImage.stbi_load(filename, w, h, c, 4);
        width = w.get();
        height = h.get();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);
        stack.close();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public int getTextureNumber() { return textureNumber; }

    public void bind() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + textureNumber);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }
}
