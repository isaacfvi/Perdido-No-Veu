package com.mygdx.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.TileMap;
import com.mygdx.utils.Animation;
import com.mygdx.core.Assets;
import com.mygdx.pathfiding.Pathfinding;

public class Fantasma extends Entidade {

    Vector2 dir = new Vector2();
    Pathfinding pathfinding;

    public static Fantasma create(Assets assets, int velocidade, float iniX, float iniY, Jogador jogador, TileMap[][] map) {
        Animation anim = new Animation(assets, "Fantasma", 6, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Fantasma(hitbox, velocidade, anim, jogador, map);
    }

    public Fantasma(Rectangle hitbox, int velocidade, Animation anim, Jogador jogador, TileMap[][] map) {
        super(hitbox, velocidade, anim);

        pathfinding = new Pathfinding(this, jogador, map, dir);
    }

    public void update(float delta){
        super.update(delta);

        pathfinding.path(delta);
        move(dir, delta);
    }

}
