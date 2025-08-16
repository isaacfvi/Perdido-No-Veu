package com.mygdx.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.utils.Animation;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Consts;

public class Fantasma extends Entidade {

    Jogador jogador;
    Vector2 dir = new Vector2();

    public static Fantasma create(Assets assets, int velocidade, float iniX, float iniY, Jogador jogador) {
        Animation anim = new Animation(assets, "Fantasma", 6, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Fantasma(hitbox, velocidade, anim, jogador);
    }

    public Fantasma(Rectangle hitbox, int velocidade, Animation anim, Jogador jogador) {
        super(hitbox, velocidade, anim);
        this.jogador = jogador;
    }

    public void update(float delta){
        super.update(delta);
        super.move(dir.set(-1, 1), delta);
    }

    /*public Vector2 calcularProximoPassoAStar(TileMap mapa, Vector2 start, Vector2 target) {

    }*/

}
