package com.minecraftwithcommands.App;

import com.minecraftwithcommands.Engine.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;

public class Renderer {
    private static float vertices[] = {
            -0.5f, -0.5f, -0.5f,  0.0f, 0.0f, 0.0f, 0.0f, -1.0f,
             0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 0.0f, 0.0f, -1.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 0.0f, 0.0f, -1.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 0.0f, 0.0f, -1.0f,
            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 0.0f, 0.0f, 0.0f, -1.0f,

            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
             0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 0.0f, 0.0f, 1.0f,

            -0.5f,  0.5f,  0.5f,  0.0f, 1.0f, -1.0f, 0.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,  1.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 1.0f, -1.0f, 0.0f, 0.0f,

             0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
             0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
             0.5f, -0.5f, -0.5f,  1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
             0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
             0.5f,  0.5f,  0.5f,  0.0f, 1.0f, 1.0f, 0.0f, 0.0f,

            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
             0.5f, -0.5f, -0.5f,  1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
             0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
             0.5f, -0.5f,  0.5f,  1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f, 0.0f, -1.0f, 0.0f,

            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
             0.5f,  0.5f, -0.5f,  1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
             0.5f,  0.5f,  0.5f,  1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f, 0.0f, 1.0f, 0.0f
    };
    private static VertexArray vao = new VertexArray();
    private static VertexBuffer vbo = new VertexBuffer();
    private static Shader shader = new Shader("res/basic.vertex", "res/basic.fragment");
    private static Texture dirtTexture = new Texture("res/blocks.png");

    public static void init() {
        vao.bind();
        vbo.bufferData(vertices, 3);
        vao.unbind();
    }

    public static void render(World world, Camera camera) {
        dirtTexture.bind();
        vao.bind();
        vbo.bind();
        shader.bind();
        shader.setUniform("uLight.direction", new Vector3f(1.0f, -1.0f, 0.7f));
        shader.setUniform("uLight.ambient", new Vector3f(0.4f, 0.4f, 0.4f));
        shader.setUniform("uLight.diffuse", new Vector3f(1.0f, 1.0f, 1.0f));
        shader.setUniform("uView", camera.getLookAtMatrix());
        shader.setUniform("uPerspective", camera.getPerspectiveMatrix());

        for (int z = 0; z < world.DEPTH; z++) {
            for (int y = 0; y < world.HEIGHT; y++) {
                for (int x = 0; x < world.WIDTH; x++) {
                    renderChunk(world.getChunks()[z][y][x]);
                }
            }
        }
        shader.unbind();
        vao.unbind();
        vbo.unbind();
    }

    private static void renderChunk(Chunk chunk) {
        for (int z = 0; z < Chunk.DEPTH; z++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int x = 0; x < Chunk.WIDTH; x++) {
                    Block block = chunk.getBlocks()[z][y][x];
                    if (block == null)
                        continue;

                    Vector3f blockAbsolutePos = new Vector3f(block.getPositionInChunk().x + chunk.getPosition().x * Chunk.WIDTH,
                            block.getPositionInChunk().y + chunk.getPosition().y * Chunk.HEIGHT,
                            block.getPositionInChunk().z + chunk.getPosition().z * Chunk.DEPTH);

                    if (block.getType() == 0) {
                        continue;
                    }
                    Matrix4f model = new Matrix4f().translate(blockAbsolutePos);
                    shader.setUniform("uModel", model);

                    float v;
                    if (block.getType() == 1)
                        v = 4.0f/5.0f;
                    else if (block.getType() == 2)
                        v = 3.0f/5.0f;
                    else if (block.getType() == 3)
                        v = 2.0f/5.0f;
                    else if (block.getType() == 4)
                        v = 1.0f/5.0f;
                    else
                        v = 0.0f;

                    Matrix4f texScale = new Matrix4f().scale(new Vector3f(1.0f/6.0f, 1.0f/5.0f, 0.0f));
                    shader.setUniform("uTexScale", texScale);

                    if (z == 0 || chunk.getBlocks()[z - 1][y][x].getType() == 0) {
                        Matrix4f texTranslate = new Matrix4f().translate(new Vector3f(0.0f, v, 0.0f));
                        shader.setUniform("uTexTranslate", texTranslate);
                        GL15.glDrawArrays(GL15.GL_TRIANGLES, 0, 6);
                    }
                    if (z == Chunk.DEPTH - 1 || chunk.getBlocks()[z + 1][y][x].getType() == 0) {
                        Matrix4f texTranslate = new Matrix4f().translate(new Vector3f(1.0f/6.0f, v, 0.0f));
                        shader.setUniform("uTexTranslate", texTranslate);
                        GL15.glDrawArrays(GL15.GL_TRIANGLES, 6, 6);
                    }
                    if (x == 0 || chunk.getBlocks()[z][y][x - 1].getType() == 0) {
                        Matrix4f texTranslate = new Matrix4f().translate(new Vector3f(1.0f/3.0f, v, 0.0f));
                        shader.setUniform("uTexTranslate", texTranslate);
                        GL15.glDrawArrays(GL15.GL_TRIANGLES, 12, 6);
                    }
                    if (x == Chunk.WIDTH - 1 || chunk.getBlocks()[z][y][x + 1].getType() == 0) {
                        Matrix4f texTranslate = new Matrix4f().translate(new Vector3f(0.5f, v, 0.0f));
                        shader.setUniform("uTexTranslate", texTranslate);
                        GL15.glDrawArrays(GL15.GL_TRIANGLES, 18, 6);
                    }
                    if (y == 0 || chunk.getBlocks()[z][y - 1][x].getType() == 0) {
                        Matrix4f texTranslate = new Matrix4f().translate(new Vector3f(5.0f/6.0f, v, 0.0f));
                        shader.setUniform("uTexTranslate", texTranslate);
                        GL15.glDrawArrays(GL15.GL_TRIANGLES, 24, 6);
                    }
                    if (y == Chunk.HEIGHT - 1 || chunk.getBlocks()[z][y + 1][x].getType() == 0) {
                        Matrix4f texTranslate = new Matrix4f().translate(new Vector3f(2.0f/3.0f, v, 0.0f));
                        shader.setUniform("uTexTranslate", texTranslate);
                        GL15.glDrawArrays(GL15.GL_TRIANGLES, 30, 6);
                    }
                }
            }
        }
    }

    public static void terminate() {
        shader.delete();
        vbo.delete();
        vao.delete();
        dirtTexture.delete();
    }
}
