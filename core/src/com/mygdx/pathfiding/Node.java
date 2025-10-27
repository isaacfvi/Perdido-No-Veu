package com.mygdx.pathfiding;

public class Node {

    public int x, y;
    public int gCost, hCost;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int fCost() { return gCost + hCost; }
}
