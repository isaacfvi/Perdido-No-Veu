package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Consts;
import com.mygdx.utils.MeuInputProcessor;

public class Entidade {
    private int velocidade;
    private final Animation anim;
    private MeuInputProcessor meuInput;

    private boolean able_to_move = true;
    private final Rectangle hitbox;
    private final Rectangle future_hitbox;

    private final Vector2 centerCache = new Vector2();

    public Entidade(Rectangle hitbox, int velocidade, Animation anim) {
        this.hitbox = hitbox;
        this.future_hitbox = new Rectangle(hitbox);
        this.velocidade = velocidade;
        this.anim = anim;
    }

    public Entidade(Rectangle hitbox, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        this.hitbox = hitbox;
        this.future_hitbox = new Rectangle(hitbox);
        this.velocidade = velocidade;
        this.anim = anim;
        this.meuInput = meuInput;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public Vector2 getPosition() {
        return centerCache;
    }

    public Rectangle getFuture_hitbox() {
        return future_hitbox;
    }

    public MeuInputProcessor getMeuInput() {
        return meuInput;
    }

    public void addPosition(Vector2 position) {
        this.hitbox.setPosition(hitbox.x + position.x, hitbox.y + position.y);
    }

    public void setDirecao(int direcao){
        if(anim != null){
            anim.setDirecao(direcao);
        }
    }

    public void setMovementPermition(boolean movement){
        this.able_to_move = movement;
    }

    public void move(float x, float y){
        if(x < 0){
            setDirecao(Consts.DIREITA);
        }
        else{
            setDirecao(Consts.ESQUERDA);
        }

        future_hitbox.setPosition(hitbox.x + x * velocidade, hitbox.y + y * velocidade);

        if(able_to_move){
            hitbox.set(future_hitbox);
        }

    }

    public void update(float delta) {

        if (meuInput != null && !(meuInput.isMoving())) {
            anim.reset();
        }
        hitbox.getCenter(centerCache);
        anim.update(delta);
    }

    public void draw(SpriteBatch batch){
        anim.draw(batch, centerCache);
    }

    public void dispose(){
        anim.dispose();
    }
}
