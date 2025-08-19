package com.mygdx.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Timer;
import com.mygdx.world.TileMap;
import com.mygdx.utils.Animation;
import com.mygdx.core.Assets;
import com.mygdx.pathfiding.Pathfinding;

public class Fantasma extends Entidade {

    private Vector2 dir = new Vector2();
    private Pathfinding pathfinding;
    private boolean playerDetected = false;

    private int numRays = 64;
    private Ray[] rays = new Ray[numRays];
    private Timer rayTimer = new Timer(0.4f);

    public static Fantasma create(Assets assets, int velocidade, float iniX, float iniY, Jogador jogador, TileMap[][] map) {
        Animation anim = new Animation(assets, "Fantasma", 6, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Fantasma(hitbox, velocidade, anim, jogador, map);
    }

    public Fantasma(Rectangle hitbox, int velocidade, Animation anim, Jogador jogador, TileMap[][] map) {
        super(hitbox, velocidade, anim);

        float angleStep = (float)360/numRays;
        for(int i = 0; i < numRays; i++) {
            rays[i] = new Ray(new Rectangle(0, 0, 2, 2), angleStep * i, 20);
        }

        pathfinding = new Pathfinding(this, jogador, map, dir);
    }

    public void update(float delta){
        super.update(delta);

        if(rayTimer.checkTimer(delta)) lookUp();

        if(playerDetected) pathfinding.path(delta);
        move(dir, delta);
    }

    public void lookUp(){
        for(Ray ray : rays){
            ray.startMovement(getPosition().cpy());
            if(ray.isDetectedPlayer()){
                playerDetected = true;
                break;
            }
        }
    }

}
