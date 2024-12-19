package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Consts;

public class Fantasma {

    private Texture texture;
    private Sprite[][] sprite;
    private Vector2 posicao;

    private int direcao = 0;
    private int velocidade = 350;

    private int currentframe;
    private float tempoAcumuladoSprite;
    private float frameDuration = 0.25f;

    private byte geral_state;

    public Fantasma(Assets assets) {
        this.posicao = new Vector2(1000, 1000);
        this.texture = assets.getTexture("Fantasma");
        this.sprite = new Sprite[2][6];

        TextureRegion frames[][] = TextureRegion.split(texture, 32, 32);
        int scl = 10; // redimensiona a sprint

        for (int i = 0; i < sprite.length; i++) {
            for (int j = 0; j < sprite[i].length; j++) {
                sprite[i][j] = new Sprite(frames[i][j]);
                sprite[i][j].setSize(scl*frames[i][j].getRegionWidth(), scl*frames[i][j].getRegionHeight());
                this.sprite[i][j].setPosition(posicao.x, posicao.y);
            }
        }
    }

    public void update(float delta, Vector2 alvo){
        switch (geral_state) {
            case Consts.PATRULHA:
                if(alvo.dst(posicao) < 1000){
                    this.geral_state = Consts.HUNT;
                    break;
                }
                //patrulha(delta, alvo);
                break;
            case Consts.HUNT:
                if(alvo.dst(posicao) > 1000){
                    this.geral_state = Consts.PATRULHA;
                }
                this.move(alvo,delta);
                break;
            default:
                //if(alvo.dst(posicao) < 20){}
                break;
        }

        tempoAcumuladoSprite += delta;
        if(tempoAcumuladoSprite >= frameDuration){
            tempoAcumuladoSprite = 0;
            currentframe = (currentframe < sprite[0].length-1) ? currentframe+1 : 0;
        }
    }

    public void move(Vector2 alvo, float delta) {

        Vector2 direcao = new Vector2(alvo).sub(posicao);

        //Persegue o jogador
        if(direcao.len() > 20) {
            direcao.nor().scl(velocidade*delta);
            posicao.add(direcao);
            this.direcao = direcao.x > 0 ? 0 : 1;
        }
    }

    public void draw(SpriteBatch batch) {
        float width = sprite[0][0].getWidth()/2;
        float height = sprite[0][0].getHeight()/2;

        for (Sprite[] sprites : sprite) {
            for (Sprite value : sprites) {
                value.setPosition(posicao.x - width, posicao.y - height);
            }
        }
        sprite[direcao][currentframe].draw(batch);
    }

    public void dispose(){
        texture.dispose();
    }

    public Rectangle gethitboxRectangle() {
        return sprite[direcao][currentframe].getBoundingRectangle();
    }

    private void patrulha(float delta, Vector2 alvo){

    }
}
