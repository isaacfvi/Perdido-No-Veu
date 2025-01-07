package com.mygdx.proceduralGeneration;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {

    private TileType tileType;
    private boolean isCollidable;

    private int x, y;

    public TileMap(TileType tileType, boolean isCollidable, int x, int y) {
        this.tileType = tileType;
        this.x = x;
        this.y = y;
        this.isCollidable = isCollidable;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public boolean isCollidable() {
        return isCollidable;
    }
}
