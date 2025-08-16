package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Assets;
import com.mygdx.utils.MeuInputProcessor;

import java.awt.*;

public class Jogador extends Entidade{

    private boolean alive;

    public static Jogador create(MeuInputProcessor meuInput, Assets assets, int velocidade, float iniX, float iniY) {
        Animation anim = new Animation(assets, "Player", 3, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Jogador(hitbox, velocidade, anim, meuInput);
    }

    private Jogador(Rectangle hitbox, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        super(hitbox, velocidade, anim, meuInput);
        this.alive = true;
    }

    @Override
    public void update(float delta){
        super.update(delta);
        super.getMeuInput().update(this, delta);
    }

    public void onCollide(Entidade other) {
        if(other instanceof Fantasma){
            died();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void died(){
        alive = false;
    }

}
