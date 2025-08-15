package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Assets;
import com.mygdx.utils.MeuInputProcessor;

import java.awt.*;

public class Jogador extends Entidade{

    private boolean alive;

    public Jogador(MeuInputProcessor meuInput, Assets assets, int velocidade) {
        super(new Rectangle(50, 50, 10, 10), velocidade, new Animation("Player", assets, 2, 3), meuInput);
        this.alive = true;
    }

    @Override
    public void update(float delta){
        super.update(delta);
        super.getMeuInput().update(this, delta);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void dispose(){
        super.dispose();
    }

    public boolean isAlive() {
        return alive;
    }

    public void died(){
        alive = false;
    }

}
