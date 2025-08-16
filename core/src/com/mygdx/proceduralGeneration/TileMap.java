package com.mygdx.proceduralGeneration;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TileMap {

    private final Sprite sprite;
    private final Rectangle hitbox;
    private final boolean isCollidable;

    public TileMap(Sprite sprite, Vector2 position, boolean isCollidable) {
        this.sprite = sprite;
        this.hitbox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
        this.sprite.setPosition(position.x, position.y);
        this.isCollidable = isCollidable;
    }

    public boolean isSolid(Rectangle hitbox) {
        return this.hitbox.overlaps(hitbox);
    }

    public Rectangle getHitbox() { return hitbox; }

    public boolean isCollidable() {return isCollidable;}

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    @Override
    public String toString() {
        return "isCollidable: " + isCollidable;
    }
}
