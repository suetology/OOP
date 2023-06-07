package com.minecraftwithcommands.App;

import org.joml.Vector3f;

public class Block {
    private Vector3f positionInChunk;
    private int type;

    public Block(Vector3f positionInChunk, int type) {
        this.positionInChunk = positionInChunk;
        this.type = type;
    }

    public Vector3f getPositionInChunk() { return positionInChunk; }
    public int getType() { return type; }
    public void set(int type) { this.type = type; }
    public void destroy() { type = 0; }
}
