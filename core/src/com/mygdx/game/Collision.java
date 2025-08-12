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

    public void inscreverParede(TileMap wall) {
        walls.add(wall);
    }

    public static Collision getInstance(){
        if(instance == null){
            instance = new Collision();
        }
        return instance;
    }

    public boolean checkCollision(Vector2 position) {
        for(TileMap wall : walls){
            if(wall.isSolid(position)){return true;}
        }
        return false;
    }
}
