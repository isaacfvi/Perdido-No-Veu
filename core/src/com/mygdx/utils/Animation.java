package com.mygdx.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Animation {

    private Sprite[][] sprite;

    private int currentframe;
    private Timer timer = new Timer(0.25f);

    private int direcao;

    public Animation(Assets assets, String name, int col, int rows) {
        this.direcao = Consts.DIREITA;

        sprite = assets.getSprites("Entidades", name, col, rows);
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, sprite[0][0].getWidth(), sprite[0][0].getHeight());
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public void reset() {
        currentframe = 0;
        timer.reset();
    }

    public void update(float delta) {
        if(timer.checkTimer(delta)) currentframe = (currentframe < sprite[0].length-1) ? currentframe+1 : 0;

    }

    public void draw(SpriteBatch batch, Vector2 pos) {
        sprite[direcao][currentframe].setPosition(pos.x - sprite[direcao][currentframe].getWidth()/2, pos.y - sprite[direcao][currentframe].getHeight()/2);
        sprite[direcao][currentframe].draw(batch);
    }
}
