package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Consts;
import com.mygdx.utils.MeuInputProcessor;

public class Entidade {
    private Vector2 position;
    private int velocidade;
    private Animation anim;
    private MeuInputProcessor meuInput;

    private boolean able_to_move;
    private Vector2 future_position;


    public Entidade(Vector2 position, int velocidade, Animation anim) {
        this.position = position;
        this.future_position = new Vector2(position.x, position.y);
        this.velocidade = velocidade;
        this.anim = anim;
        this.able_to_move = true;
    }

    public Entidade(Vector2 position, int velocidade, Animation anim, MeuInputProcessor meuInput) {
        this.position = position;
        this.future_position = new Vector2(position.x, position.y);
        this.velocidade = velocidade;
        this.anim = anim;
        this.meuInput = meuInput;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getFuturePosition() { return future_position; }

    public MeuInputProcessor getMeuInput() {
        return meuInput;
    }

    public void addPosition(Vector2 position) {
        this.position.add(position);
    }

    public void setDirecao(int direcao){
        if(anim != null){
            anim.setDirecao(direcao);
        }
    }

    public void setMovementPermition(boolean movement){
        this.able_to_move = movement;
    }

    public void move(float x, float y){
        if(x < 0){
            setDirecao(Consts.DIREITA);
        }
        else{
            setDirecao(Consts.ESQUERDA);
        }

        future_position.set(position).add(x * velocidade, y * velocidade);

        if(able_to_move){
            position.set(future_position);
        }

    }

    public void update(float delta) {

        if (meuInput != null && !(meuInput.isMoving())) {
            anim.reset();
        }

        anim.update(delta);
    }

    public void draw(SpriteBatch batch){
        anim.draw(batch, position);
    }

    public void dispose(){
        anim.dispose();
    }
}
