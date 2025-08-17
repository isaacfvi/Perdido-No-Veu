package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.entities.Entidade;
import com.mygdx.proceduralGeneration.TileMap;

import java.util.ArrayList;

public class Collision {

    private static Collision instance;

    private TileMap[][] map;
    private ArrayList<Entidade> entities;

    private int tileWidth, tileHeight;

    private Collision() {
        entities = new ArrayList<>();
    }

    public static Collision getInstance() {
        if (instance == null) {
            instance = new Collision();
        }
        return instance;
    }

    public void setUpMap(TileMap[][] map) {
        this.map = map;

        this.tileWidth = (int)map[0][0].getHitbox().width;
        this.tileHeight = (int)map[0][0].getHitbox().height;
    }

    public void inscreverEntidade(Entidade entidade) { entities.add(entidade); }

    public boolean checkMapCollision(Rectangle hitbox) {

        int startX = Math.max(0, (int)(hitbox.x / tileWidth));
        int startY = Math.max(0, (int)(hitbox.y / tileHeight));
        int endX = Math.min(map.length-1, (int)((hitbox.x + hitbox.width) / tileWidth));
        int endY = Math.min(map[0].length-1, (int)((hitbox.y + hitbox.height) / tileHeight));

        for(int y = startY; y <= endY; y++) {
            for(int x = startX; x <= endX; x++) {
                if(map[x][y].isSolid(hitbox)) return false;
            }
        }
        return true;
    }

    public void checkEntitiesCollision(Entidade e1){
        for(Entidade e2 : entities){
            if(e2.equals(e1)){ continue; }
            if(e1.getHitbox().overlaps(e2.getHitbox())){
                e1.onCollide(e2);
                e2.onCollide(e1);
            }
        }
    }
}
