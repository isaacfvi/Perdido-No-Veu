package com.mygdx.proceduralGeneration;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TileMap {


    private Vector2 position;
    private final Sprite sprite;
    private final Rectangle hitbox;
    private final boolean isCollidable;

    public TileMap(Sprite sprite, Vector2 position, boolean isCollidable) {
        this.sprite = sprite;
        this.position = position;
        this.hitbox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
        this.sprite.setPosition(position.x, position.y);
        this.isCollidable = isCollidable;
    }

    public Vector2 getPosition() {return position;}

    public boolean isSolid(Vector2 point) {
        return hitbox.contains(point);
    }

    public boolean isCollidable() {return isCollidable;}

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
    public void dispose(){}
}
