package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.utils.Animation;
import com.mygdx.core.Assets;
import com.mygdx.utils.MeuInputProcessor;
import com.mygdx.utils.Timer;
import com.mygdx.world.Collision;

public class Jogador extends Entidade{

    private boolean alive;
    private MeuInputProcessor meuInput;
    private Salt salt;

    private final int run = 120;
    private final int walk = 80;

    public static Jogador create(MeuInputProcessor meuInput, Assets assets, int velocidade, float iniX, float iniY) {
        Animation anim = new Animation(assets, "Player", 3, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Jogador(assets, hitbox, velocidade, anim, meuInput);
    }

    private Jogador(Assets assets, Rectangle hitbox, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        super(hitbox, velocidade, anim);
        this.meuInput = meuInput;
        this.alive = true;
        salt = Salt.create(assets);

    }

    @Override
    public void update(float delta){
        super.update(delta);

        if(meuInput != null) {
            if(!meuInput.isMoving())
                super.animReset();

            if(meuInput.isRunning())
                this.setVelocidade(run);
            else
                this.setVelocidade(walk);

            if(meuInput.isSpace()){
                salt.active(this.getPosition());
            }

            meuInput.update(this, delta);
        }

        salt.update(delta);
    }

    public void onCollide(Entidade other) {
        if(other instanceof Fantasma && !((Fantasma) other).isRunning()){
            died();
        }
        if(other instanceof Sapato){
            died();
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void died(){
        alive = false;
    }

    public void draw(SpriteBatch batch){
        super.draw(batch);
        salt.draw(batch);
    }

}
