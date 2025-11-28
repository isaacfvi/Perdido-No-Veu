package com.mygdx.world;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.core.Consts;

public class TileMap {

    private final Sprite sprite;
    private final Rectangle hitbox;
    private final boolean isCollidable;
    private float playerPath;

    private static Texture overlayTexture;
    private Sprite overlaySprite;

    public TileMap(Sprite sprite, Vector2 position, boolean isCollidable) {
        this.sprite = sprite;
        this.hitbox = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
        this.sprite.setPosition(position.x, position.y);
        this.isCollidable = isCollidable;
        this.playerPath = 0;

        if(Consts.DEBUG) {
            if (overlayTexture == null) {
                createOverlayTexture();
            }
            this.overlaySprite = new Sprite(overlayTexture);
            this.overlaySprite.setPosition(position.x, position.y);
            this.overlaySprite.setSize(sprite.getWidth(), sprite.getHeight());
        }
    }

    private void createOverlayTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        overlayTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public boolean isSolid(Rectangle hitbox) {
        return this.hitbox.overlaps(hitbox) && isCollidable;
    }

    public Rectangle getHitbox() { return hitbox; }

    public boolean isCollidable() {return isCollidable;}

    public void draw(SpriteBatch batch){
        sprite.draw(batch);

        if (playerPath > 0 && Consts.DEBUG && overlaySprite != null) {
            Color originalColor = overlaySprite.getColor();
            overlaySprite.setColor(1, 0, 0, playerPath);
            overlaySprite.draw(batch);
            overlaySprite.setColor(originalColor);
        }
    }

    public void increasePath(float quant){
        playerPath = Math.min(1, playerPath + quant);
    }

    public void decreasePath(float quant){
        playerPath = Math.max(0, playerPath - quant);
    }

    public float getPlayerPath() {
        return playerPath;
    }

    @Override
    public String toString() {
        return "isCollidable: " + isCollidable + ", playerPath: " + playerPath;
    }

    public void dispose() {
        if (overlayTexture != null) {
            overlayTexture.dispose();
            overlayTexture = null;
        }
    }
}