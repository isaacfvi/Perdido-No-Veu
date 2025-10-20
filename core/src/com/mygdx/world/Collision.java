package com.mygdx.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.core.Consts;
import com.mygdx.entities.Entidade;

import java.util.ArrayList;

public class Collision {

    private static Collision instance;

    private TileMap[][] map;
    private ArrayList<Entidade> entities;

    private Vector2 aux = new Vector2();

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
    }

    public void inscreverEntidade(Entidade entidade) { entities.add(entidade); }

    public void removerEntidade(Entidade entidade) { entities.remove(entidade); }

    public boolean checkMapCollision(Rectangle hitbox) {

        int startX = Math.max(0, (int)(hitbox.x / Consts.TILE_SIZE));
        int startY = Math.max(0, (int)(hitbox.y / Consts.TILE_SIZE));
        int endX = Math.min(map.length-1, (int)((hitbox.x + hitbox.width) / Consts.TILE_SIZE));
        int endY = Math.min(map[0].length-1, (int)((hitbox.y + hitbox.height) / Consts.TILE_SIZE));

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

    public TileMap getTile(Rectangle hitbox) {
        aux = hitbox.getCenter(aux);
        int x = (int)(aux.x / Consts.TILE_SIZE);
        int y = (int)(aux.y / Consts.TILE_SIZE);

        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) return null;

        return map[x][y];
    }
}
