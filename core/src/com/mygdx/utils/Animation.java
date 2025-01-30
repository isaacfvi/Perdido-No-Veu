package com.mygdx.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Animation {

    private Texture texture;
    private Sprite[][] sprite;

    private int currentframe;
    private float tempoAcumuladoSprite;
    private float frameDuration = 0.25f;

    private int direcao;

    public Animation(String texture, Assets assets, int spriteX, int spriteY) {
        this.texture = assets.getTexture(texture);
        this.sprite = new Sprite[spriteX][spriteY];
        this.direcao = Consts.DIREITA;

        TextureRegion[][] frames = TextureRegion.split(this.texture, 32, 32);
        int scl = 10; // redimensiona a sprint

        for (int i = 0; i < sprite.length; i++) {
            for (int j = 0; j < sprite[i].length; j++) {
                sprite[i][j] = new Sprite(frames[i][j]);
                sprite[i][j].setSize(scl*frames[i][j].getRegionWidth(), scl*frames[i][j].getRegionHeight());
            }
        }
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public void reset() {
        currentframe = 0;
        tempoAcumuladoSprite = 0;
    }

    public void update(float delta) {
        tempoAcumuladoSprite += delta;
        if(tempoAcumuladoSprite >= frameDuration){
            tempoAcumuladoSprite = 0;
            currentframe = (currentframe < sprite[0].length-1) ? currentframe+1 : 0;
        }
    }

    public void draw(SpriteBatch batch, Vector2 pos) {
        sprite[direcao][currentframe].setPosition(pos.x - sprite[direcao][currentframe].getWidth()/2, pos.y - sprite[direcao][currentframe].getHeight()/2);
        sprite[direcao][currentframe].draw(batch);
    }

    public void dispose() {
        texture.dispose();
    }



}
