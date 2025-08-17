package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.QuadTreeFloat;
import com.mygdx.game.Collision;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Consts;
import com.mygdx.utils.MeuInputProcessor;

public class Entidade {
    private int velocidade;
    private final Animation anim;

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

    public Vector2 getPosition() {
        return centerCache;
    }

    public Rectangle getHitbox() { return hitbox; }

    public void setDirecao(int direcao){
        if(anim != null) anim.setDirecao(direcao);
    }

    public void move(Vector2 dir, float delta){
        if (dir.x != 0) setDirecao(dir.x < 0 ? Consts.DIREITA : Consts.ESQUERDA);

        dir.nor();
        nextMoviment.set(dir.x * velocidade * delta, dir.y * velocidade * delta);

        futureHitbox.setPosition(hitbox.x + nextMoviment.x, hitbox.y);
        if(Collision.getInstance().checkMapCollision(futureHitbox)) {
            hitbox.x += nextMoviment.x;
        }

        futureHitbox.setPosition(hitbox.x, hitbox.y + nextMoviment.y);
        if(Collision.getInstance().checkMapCollision(futureHitbox)) {
            hitbox.y += nextMoviment.y;
        }

        Collision.getInstance().checkEntitiesCollision(this);
    }

    public void animReset(){
        if(anim != null) anim.reset();
    }

    public void update(float delta) {
        hitbox.getCenter(centerCache);

        if(anim != null) anim.update(delta);
    }

    public void onCollide(Entidade other) {}

    public void draw(SpriteBatch batch){
        if(anim != null) anim.draw(batch, centerCache);
    }
}
