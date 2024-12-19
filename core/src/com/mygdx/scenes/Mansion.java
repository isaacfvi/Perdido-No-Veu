package com.mygdx.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Assets;

public class Mansion {

    private Assets assets;
    private Texture texture;
    private Sprite sprite;
    private Vector2 posicao;

    public Mansion(Assets assets) {
        this.assets = assets;
        this.texture =  assets.getTexture("Piso");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(320, 320);
        this.posicao = new Vector2();
    }

    public void update(){
        sprite.setPosition(posicao.x - sprite.getWidth()/2, posicao.y - sprite.getHeight()/2);
        sprite.setSize(320, 320);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);

    }

    public void dispose(){
        texture.dispose();
    }

}
