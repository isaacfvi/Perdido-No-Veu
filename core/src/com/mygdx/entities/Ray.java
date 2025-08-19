package com.mygdx.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.Collision;
import com.mygdx.world.TileMap;

public class Ray extends Entidade {

    private final float angle;
    private final int maxSteps = 180;
    private int steps;

    private boolean detectedPlayer = false;
    private TileMap bestTile;

    private Vector2 dir = new Vector2();
    private Vector2 pos = new Vector2();

    public Ray(Rectangle hitbox, float angle, int velocidade) {
        super(hitbox, velocidade);
        this.angle = angle;
    }

    public int startMovement(Vector2 start) {
        detectedPlayer = false;
        steps = 0;
        bestTile = null;

        super.getHitbox().setPosition(start);
        pos.set(start);

        while(Collision.getInstance().checkMapCollision(super.getHitbox()) && steps < maxSteps) {
            dir.set(0, 1).nor().rotateDeg(angle);
            super.getHitbox().setPosition(pos.add(dir));

            pathing();

            Collision.getInstance().checkEntitiesCollision(this);
            steps++;
        }

        return steps;
    }

    public void pathing(){
        TileMap tile;

        tile = Collision.getInstance().getTile(super.getHitbox());
        if (tile == null) return;
        if(bestTile == null) bestTile = tile;
        else if(bestTile.getPlayerPath() < tile.getPlayerPath()) bestTile = tile;
    }

    public boolean isDetectedPlayer() {
        return detectedPlayer;
    }

    public void onCollide(Entidade e) {
        if(e instanceof Jogador) detectedPlayer = true;
    }

    public TileMap getBestTile() { return bestTile; }

}
