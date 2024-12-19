package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Assets;
import com.mygdx.utils.MeuInputProcessor;

public class Jogador {

    private Assets assets;
    private Texture texture;
    private Sprite[][] sprite;
    private Vector2 posicao, ultimaPosicao;
    private int velocidade = 500;

    MeuInputProcessor meuInput;
    private boolean alive;
    private int direcao = 0;

    private int currentframe;
    private float tempoAcumulado;
    private float frameDuration = 0.25f;

    public Jogador(MeuInputProcessor meuInput, Assets assets) {
        this.assets = assets;
        this.posicao = new Vector2();
        this.ultimaPosicao = new Vector2();
        this.texture = assets.getTexture("Player");
        this.sprite = new Sprite[2][3];

        TextureRegion frames[][] = TextureRegion.split(texture, 32, 32);
        int scl = 10; // redimensiona a sprint
        for (int i = 0; i < sprite.length; i++) {
            for (int j = 0; j < sprite[i].length; j++) {
                sprite[i][j] = new Sprite(frames[i][j]);
                sprite[i][j].setSize(scl*frames[i][j].getRegionWidth(), scl*frames[i][j].getRegionHeight());
                this.sprite[i][j].setPosition(posicao.x, posicao.y);
            }
        }

        this.meuInput = meuInput;
        this.alive = true;
    }

    public void update(float delta){
        meuInput.update(this, delta);

        tempoAcumulado += delta;
        if (!posicao.epsilonEquals(ultimaPosicao, 0.1f)) {
            tempoAcumulado += delta;
            if (tempoAcumulado >= frameDuration) {
                tempoAcumulado = 0;
                currentframe = (currentframe < sprite[0].length-1) ? currentframe + 1 : 0;
            }

            ultimaPosicao.set(posicao);
        } else {
            currentframe = 0;
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

    public void move(float x, float y){
        this.direcao = x < 0 ? 0 : 1;
        posicao.add(x * velocidade, y * velocidade);
    }

    public Vector2 getPosicao() {
        return posicao;
    }

    public Rectangle gethitbox() {
        return sprite[direcao][currentframe].getBoundingRectangle();
    }

    public boolean isAlive() {
        return alive;
    }
    public void died(){
        alive = false;
    }

}
