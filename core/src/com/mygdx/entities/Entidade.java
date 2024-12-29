package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Consts;
import com.mygdx.utils.MeuInputProcessor;

public class Entidade {
    private Vector2 position;
    private int velocidade;
    private Animation anim;
    private MeuInputProcessor meuInput;


    public Entidade(Vector2 position, int velocidade, Animation anim) {
        this.position = position;
        this.velocidade = velocidade;
        this.anim = anim;
    }

    public Entidade(Vector2 position, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        this.position = position;
        this.velocidade = velocidade;
        this.anim = anim;
        this.meuInput = meuInput;
    }

    public Entidade(Vector2 position, int velocidade, MeuInputProcessor meuInput) {
        this.position = position;
        this.velocidade = velocidade;
        this.meuInput = meuInput;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public Vector2 getPosition() {
        return position;
    }

    public MeuInputProcessor getMeuInput() {
        return meuInput;
    }

    public void addPosition(Vector2 position) {
        this.position.add(position);
    }

    public void setDirecao(int direcao){
        anim.setDirecao(direcao);
    }

    public void move(float x, float y){
        if(x < 0){
            setDirecao(Consts.DIREITA);
        }
        else{
            setDirecao(Consts.ESQUERDA);
        }
        position.add(x * velocidade, y * velocidade);
    }

    public void update(float delta) {
        anim.update(delta);
    }

    public void draw(SpriteBatch batch){
        anim.draw(batch, position);
    }

    public void dispose(){
        anim.dispose();
    }
}
