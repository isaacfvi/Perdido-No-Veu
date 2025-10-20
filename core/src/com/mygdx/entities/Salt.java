package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.core.Assets;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Timer;
import com.mygdx.world.Collision;

public class Salt extends Entidade {

    private int quantSalt = 3;
    private Timer cooldown = new Timer(2);
    private Boolean active = false;

    public static Salt create(Assets assets) {
        Animation anim = new Animation(assets, "Sapato", 1, 1);
        Rectangle hitbox = anim.getBounds();
        hitbox.setSize(hitbox.width, hitbox.height);
        return new Salt(hitbox, anim);
    }

    public Salt(Rectangle hitbox, Animation anim) {
        super(hitbox, 0, anim);
    }

    @Override
    public void update(float delta) {
        if(active){
            super.update(delta);

            if(cooldown.checkTimer(delta)){
                deactive();
            }
        }
    }

    public void active(Vector2 pos){
        if(quantSalt > 0){
            if(!active){
                quantSalt--;
                this.getHitbox().setPosition(pos.x - 16, pos.y - 16);
                Collision.getInstance().inscreverEntidade(this);
            }
            active = true;
        }
    }

    public void deactive(){
        Collision.getInstance().removerEntidade(this);
        active = false;
    }

    public void draw(SpriteBatch batch){
        if(active){
            super.draw(batch);
        }
    }
}
