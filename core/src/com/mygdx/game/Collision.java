package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entidade;

import java.awt.*;

public class Collision {

    private static Collision instance;

    private Array<Rectangle> walls;
    private Array<Entidade> entidades;


    public Collision() {
        this.walls = new Array<Rectangle>();
        this.entidades = new Array<Entidade>();
    }

    public void inscreverEntidade(Entidade entidade) {
        entidades.add(entidade);
    }

    public void inscreverParede(Rectangle wall) {
        //walls.add(wall);
    }

    public static Collision getInstance(){
        if(instance == null){
            instance = new Collision();
        }
        return instance;
    }

    public boolean checkCollision(int x, int y){

        System.out.println("numero de paredes: "+ walls.size);
        for(int i = 0; i < walls.size; i++){
            if(0 == walls.size%(i+1)){
                System.out.println("Parede "+ i);
                System.out.println(walls.get(i));
                System.out.println("Posicao " + x + " " + y);
            }
            if(walls.get(i).contains(x, y)){
                System.out.println("collidiu com " + walls.get(i));
                return true;
            }
        }
        return false;
    }
}
