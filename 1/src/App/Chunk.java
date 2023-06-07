package App;

import org.joml.Vector3f;

public class Chunk {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static final int DEPTH = 16;

    private Vector3f position;
    private Block[][][] blocks;

    public Chunk(Vector3f position, boolean empty, boolean upper) {
        this.position = position;
        blocks = new Block[DEPTH][HEIGHT][WIDTH];

        for (int z = 0; z < DEPTH; z++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    int type;
                    if (empty)
                        type = 0;
                    else if (upper && y == HEIGHT - 1)
                        type = 1;
                    else if (y > HEIGHT - 4)
                        type = 2;
                    else
                        type = 3;

                    blocks[z][y][x] = new Block(new Vector3f((float)x, (float)y, (float)z), type);
                }
            }
        }
    }

    public Vector3f getPosition() { return position; }
    public Block[][][] getBlocks() { return blocks; }
}
