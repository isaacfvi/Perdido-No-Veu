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

    public Fantasma(Assets assets, int velocidade) {
        super(new Rectangle(0, 0, 1, 1), velocidade, new Animation("Fantasma", assets, 2, 6));
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
