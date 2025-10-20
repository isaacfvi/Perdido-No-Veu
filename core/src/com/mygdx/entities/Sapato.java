package com.mygdx.entities;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.core.Assets;
import com.mygdx.utils.Animation;

public class Sapato extends Entidade {

    public static Sapato create(Assets assets, float iniX, float iniY) {
        Animation anim = new Animation(assets, "Sapato", 1, 1);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width, hitbox.height);
        return new Sapato(hitbox, anim);
    }

    public Sapato(Rectangle hitbox, Animation anim) {
        super(hitbox, 0, anim);
    }

}
