package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entidade;
import com.mygdx.proceduralGeneration.TileMap;

import java.awt.*;

public class Collision {

    private static Collision instance;

    private Array<TileMap> walls;
    private Array<Entidade> entidades;


    public Collision() {
        this.walls = new Array<>();
        this.entidades = new Array<>();
    }

    public void inscreverEntidade(Entidade entidade) {
        entidades.add(entidade);
    }

    public void setWalls(Array<TileMap> walls) { this.walls = walls; }

    public void update() {

        for(Entidade entidade : entidades) {
            entidade.setMovementPermition(true);
        }

        for(TileMap wall : walls){
            for(Entidade entidade : entidades){
                if(wall.isSolid(entidade.getFuturePosition())){
                    entidade.setMovementPermition(false);
                }
            }
        }
    }
}
