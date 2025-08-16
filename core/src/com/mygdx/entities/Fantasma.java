package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Consts;

public class Fantasma extends Entidade {

    private byte geral_state;
    private Vector2 alvo;

    public static Fantasma create(Assets assets, int velocidade, float iniX, float iniY) {
        Animation anim = new Animation("Fantasma", assets, 2, 6);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Fantasma(hitbox, velocidade, anim);
    }

    public Fantasma(Rectangle hitbox, int velocidade, Animation anim) {
        super(hitbox, velocidade, anim);
    }

    public void update(float delta){
        super.update(delta);
    }

    public void setAlvo(Vector2 alvo) {
        this.alvo = alvo;
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void dispose(){
        super.dispose();
    }

    private void patrulha(float delta, Vector2 alvo){

    }
}
