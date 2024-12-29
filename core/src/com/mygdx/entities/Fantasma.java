package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Consts;

public class Fantasma extends Entidade {

    private byte geral_state;
    private Vector2 alvo;

    public Fantasma(Assets assets, int velocidade) {
        super(new Vector2(), velocidade, new Animation("Fantasma", assets, 2, 6));
    }

    public void update(float delta){
        super.update(delta);
        switch (geral_state) {
            case Consts.PATRULHA:
                if(alvo.dst(super.getPosition()) < 1000){
                    this.geral_state = Consts.HUNT;
                    break;
                }
                //patrulha(delta, alvo);
                break;
            case Consts.HUNT:
                if(alvo.dst(super.getPosition()) > 1000){
                    this.geral_state = Consts.PATRULHA;
                }
                this.goTo(alvo,delta);
                break;
            default:
                //if(alvo.dst(posicao) < 20){}
                break;
        }
    }

    public void setAlvo(Vector2 alvo) {
        this.alvo = alvo;
    }

    public void goTo(Vector2 alvo, float delta) {

        Vector2 direcao = new Vector2(alvo).sub(super.getPosition());

        //Persegue o jogador
        if(direcao.len() > 20) {
            direcao.nor().scl(super.getVelocidade()*delta);
            super.addPosition(direcao);
            if(direcao.x > 0){
                super.setDirecao(Consts.DIREITA);
            }else{
                super.setDirecao(Consts.ESQUERDA);
            }
        }
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
