package com.minecraftwithcommands.Engine;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public class Shader {
    private int id;

    public Shader(String vsFilename, String fsFilename) {
        int vsID = loadShader(vsFilename, GL20.GL_VERTEX_SHADER);
        int fsID = loadShader(fsFilename, GL20.GL_FRAGMENT_SHADER);

        id = GL20.glCreateProgram();
        GL20.glAttachShader(id, vsID);
        GL20.glAttachShader(id, fsID);
        GL20.glLinkProgram(id);
        GL20.glValidateProgram(id);

        GL20.glDetachShader(id, vsID);
        GL20.glDetachShader(id, fsID);
        GL20.glDeleteShader(vsID);
        GL20.glDeleteShader(fsID);
    }

    public void bind() {
        GL20.glUseProgram(id);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void delete() {
        GL20.glDeleteShader(id);
    }

    public void setUniform(String name, float value) {
        int location = GL20.glGetUniformLocation(id, name);
        GL20.glUniform1f(location, value);
    }

    public void setUniform(String name, int value) {
        int location = GL20.glGetUniformLocation(id, name);
        GL20.glUniform1i(location, value);
    }

    public void setUniform(String name, Vector3f data) {
        int location = GL20.glGetUniformLocation(id, name);
        GL20.glUniform3f(location, data.x, data.y, data.z);
    }

    public void setUniform(String name, Matrix4f data) {
        int location = GL20.glGetUniformLocation(id, name);
        MemoryStack stack = MemoryStack.stackPush();
        GL20.glUniformMatrix4fv(location, false, data.get(stack.mallocFloat(16)));
        stack.close();
    }

    public void setUniform(String name, Matrix3f data) {
        int location = GL20.glGetUniformLocation(id, name);
        MemoryStack stack = MemoryStack.stackPush();
        GL20.glUniformMatrix4fv(location, false, data.get(stack.mallocFloat(9)));
        stack.close();
    }

    private static int loadShader(String filename, int type) {
        StringBuilder shaderCode = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderCode.append(line).append('\n');
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file");
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            throw new RuntimeException("Could not compile shader");
        }
        return shaderID;
    }
}
