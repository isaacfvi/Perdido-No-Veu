package com.mygdx.pathfiding;

public class Node {

    public int x, y;
    public int gCost, hCost, fCost;
    public Node parent;
    public boolean isWalkable;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int fCost() { return gCost + hCost; }
}
