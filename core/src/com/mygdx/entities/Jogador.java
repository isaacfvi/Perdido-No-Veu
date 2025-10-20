package com.mygdx.entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.utils.Animation;
import com.mygdx.core.Assets;
import com.mygdx.utils.MeuInputProcessor;

public class Jogador extends Entidade{

    private boolean alive;
    private MeuInputProcessor meuInput;

    public static Jogador create(MeuInputProcessor meuInput, Assets assets, int velocidade, float iniX, float iniY) {
        Animation anim = new Animation(assets, "Player", 3, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Jogador(hitbox, velocidade, anim, meuInput);
    }

    private Jogador(Rectangle hitbox, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        super(hitbox, velocidade, anim);
        this.meuInput = meuInput;
        this.alive = true;
    }

    @Override
    public void update(float delta){
        super.update(delta);

        if(meuInput != null) {
            if(!meuInput.isMoving())
                super.animReset();

            if(meuInput.isRunning())
                this.setVelocidade(120);
            else
                this.setVelocidade(80);

            meuInput.update(this, delta);
        }
    }

    public void onCollide(Entidade other) {
        if(other instanceof Fantasma){
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

}
