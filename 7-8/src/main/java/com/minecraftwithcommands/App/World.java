package com.minecraftwithcommands.App;

import org.joml.Vector3f;

public class World {
    public final int WIDTH;
    public final int HEIGHT;
    public final int DEPTH;
    private Chunk[][][] chunks;

    public World(int width, int height, int depth) {
        WIDTH = width;
        HEIGHT = height + 1;
        DEPTH = depth;

        chunks = new Chunk[DEPTH][HEIGHT][WIDTH];
        for (int z = 0; z < DEPTH; z++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    boolean upper = y == HEIGHT - 2;
                    boolean empty = y == HEIGHT - 1;
                    chunks[z][y][x] = new Chunk(new Vector3f((float)x, (float)y, (float)z), empty, upper);
                }
            }
        }
    }

    public Block getBlock(Vector3f absolutePosition) {
        Vector3f chunkPos = new Vector3f((int)absolutePosition.x / Chunk.WIDTH,
                                         (int)absolutePosition.y / Chunk.HEIGHT,
                                         (int)absolutePosition.z / Chunk.DEPTH);
        Chunk chunk = chunks[(int)chunkPos.z][(int)chunkPos.y][(int)chunkPos.x];
        if (chunk.getPosition().x != chunkPos.x || chunk.getPosition().y != chunkPos.y || chunk.getPosition().z != chunkPos.z) {
            return null;
        }
        Vector3f blockPosInChunk = new Vector3f(absolutePosition.x % Chunk.WIDTH,
                                                absolutePosition.y % Chunk.HEIGHT,
                                                absolutePosition.z % Chunk.DEPTH);
        if (blockPosInChunk.x >= Chunk.WIDTH || blockPosInChunk.y >= Chunk.HEIGHT || blockPosInChunk.z >= Chunk.DEPTH ||
            blockPosInChunk.x < 0 || blockPosInChunk.y < 0 || blockPosInChunk.z < 0)
            return null;

        Block block = chunk.getBlocks()[(int)blockPosInChunk.z][(int)blockPosInChunk.y][(int)blockPosInChunk.x];
        if (block == null)
            return null;

        return block;
    }

    public Chunk[][][] getChunks() { return chunks; }
}
