package com.minecraftwithcommands.App;

import org.joml.Vector3f;

public class Tree {

    public Tree(Vector3f position, int height, World world) {

        for (int i = 0; i < height; i++) {
            world.getBlock(new Vector3f(position).add(new Vector3f(0.0f, i, 0.0f))).set(4);
        }

        for (int z = -2; z <= 2; z++) {
            for (int x = -2; x <= 2; x++) {
                if (x == 0 && z == 0)
                    continue;
                world.getBlock(new Vector3f(position).add(new Vector3f(x, height - 2, z))).set(5);
            }
        }

        for (int z = -2; z <= 2; z++) {
            for (int x = -2; x <= 2; x++) {
                if (x == 0 && z == 0 || x == 2 && z == 2 || x == 2 && z == -2 || x == -2 && z == 2 || x == -2 && z == -2)
                    continue;
                world.getBlock(new Vector3f(position).add(new Vector3f(x, height - 1, z))).set(5);
            }
        }

        for (int z = -1; z <= 1; z++) {
            for (int x = -1; x <= 1; x++) {
                world.getBlock(new Vector3f(position).add(new Vector3f(x, height, z))).set(5);
            }
        }

        for (int z = -1; z <= 1; z++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 1 && z == 1 || x == -1 && z == 1 || x == 1 && z == -1 || x == -1 && z == -1)
                    continue;
                world.getBlock(new Vector3f(position).add(new Vector3f(x, height + 1, z))).set(5);
            }
        }
    }
}
