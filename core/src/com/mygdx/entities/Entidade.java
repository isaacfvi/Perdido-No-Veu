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

    private boolean ableMoveX = true;
    private boolean ableMoveY = true;

    private final Rectangle hitbox;
    private final Rectangle futureHitbox;

    private final Vector2 centerCache = new Vector2();
    private final Vector2 nextMoviment = new Vector2();

    public Entidade(Rectangle hitbox, int velocidade, Animation anim) {
        this.hitbox = hitbox;
        this.futureHitbox = new Rectangle(hitbox);
        this.velocidade = velocidade;
        this.anim = anim;
    }

    public Entidade(Rectangle hitbox, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        this.hitbox = hitbox;
        this.futureHitbox = new Rectangle(hitbox);
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

    public Rectangle getFutureHitboxX() {
        futureHitbox.setPosition(hitbox.x + nextMoviment.x, hitbox.y);
        return futureHitbox;
    }

    public Rectangle getFutureHitboxY() {
        futureHitbox.setPosition(hitbox.x, hitbox.y + nextMoviment.y);
        return futureHitbox;
    }

    public Rectangle getHitbox() { return hitbox; }

    public boolean checkCollision(Rectangle other) {
        return hitbox.overlaps(other);
    }

    public MeuInputProcessor getMeuInput() {
        return meuInput;
    }

    public void setDirecao(int direcao){
        if(anim != null){
            anim.setDirecao(direcao);
        }
    }

    public void setMovementPermitionX(boolean movement){
        this.ableMoveX = movement;
    }
    public void setMovementPermitionY(boolean movement){
        this.ableMoveY = movement;
    }

    public void move(Vector2 dir, float delta){
        if (dir.x != 0) setDirecao(dir.x < 0 ? Consts.DIREITA : Consts.ESQUERDA);
        dir.nor();

        if(ableMoveX) hitbox.x += nextMoviment.x;
        if(ableMoveY) hitbox.y += nextMoviment.y;

        nextMoviment.set(dir.x * velocidade * delta, dir.y * velocidade * delta);

    }

    public void update(float delta) {

        if (meuInput != null && !(meuInput.isMoving())) {
            anim.reset();
        }
        hitbox.getCenter(centerCache);
        anim.update(delta);
    }

    public void onCollide(Entidade other) {}

    public void draw(SpriteBatch batch){
        anim.draw(batch, centerCache);
    }
}
