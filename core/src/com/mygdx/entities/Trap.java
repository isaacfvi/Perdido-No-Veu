package com.mygdx.entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.core.Assets;
import com.mygdx.utils.Animation;

public class Trap extends Entidade {

    private boolean detectPlayer;

    public static Trap create(Assets assets, float iniX, float iniY, int trap) {
        Animation anim = new Animation(assets, "Trap" + trap, 1, 1);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width, hitbox.height);
        return new Trap(hitbox, anim);
    }

    public Trap(Rectangle hitbox, Animation anim) {
        super(hitbox, 0, anim);
        this.detectPlayer = false;
    }

    @Override
    public void onCollide(Entidade other) {
        if (other instanceof Jogador) {
            detectPlayer = true; }
    }

    public void desactive(){
        detectPlayer = false;
    }

    public boolean isDetectPlayer() {
        return detectPlayer;
    }

}
