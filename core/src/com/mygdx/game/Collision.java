package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entidade;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.proceduralGeneration.TileMap;

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
        Entidade e1, e2;

        for(int i = 0; i < entidades.size; i++){
            e1 = entidades.get(i);
            e1.setMovementPermitionX(true);
            e1.setMovementPermitionY(true);

            for(int j = i + 1; j < entidades.size; j++){
                e2 = entidades.get(j);

                if(e1.checkCollision(e2.getHitbox())){
                    e1.onCollide(e2);
                    e2.onCollide(e1);
                }
            }
        }

        for(TileMap wall : walls){
            for(Entidade entidade : entidades){
                if(wall.isSolid(entidade.getFutureHitboxX())) entidade.setMovementPermitionX(false);
                if(wall.isSolid(entidade.getFutureHitboxY())) entidade.setMovementPermitionY(false);
            }
        }
    }
}
