package com.minecraftwithcommands.Engine;

public enum Direction
{
    UP(0b000001),
    DOWN(0b000010),
    FORWARD(0b000100),
    BACK(0b001000),
    RIGHT(0b010000),
    LEFT(0b100000);

    public final int id;

    Direction(final int id) {
        this.id = id;
    }
}