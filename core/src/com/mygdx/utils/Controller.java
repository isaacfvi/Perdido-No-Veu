package com.mygdx.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Camera;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.game.Collision;
import com.mygdx.proceduralGeneration.TileMap;
import com.mygdx.scenes.Mansion;

public class Controller {

    private Mansion mansion;
    private Fantasma fantasma;
    private Jogador jogador;
    private Camera camera;
    private Collision collision;


    public Controller(MeuInputProcessor meuInput, Assets assets) {
        this.fantasma = Fantasma.create(assets, 35, 50, 50);
        this.jogador = Jogador.create(meuInput, assets, 80, 50, 50);
        this.camera = new Camera(jogador);
        this.mansion = new Mansion(assets);
        this.collision = new Collision();

        collision.setWalls(mansion.generateMap(assets));

        //collision.inscreverEntidade(fantasma);
        collision.inscreverEntidade(jogador);
    }

    public void update(float delta){
        if(jogador.isAlive()){
            jogador.update(delta);
            camera.update(delta);
        }
        fantasma.update(delta);
        mansion.update();

        collision.update();
    }

    public void draw(SpriteBatch batch){
        mansion.draw(batch);
        if(jogador.isAlive()){jogador.draw(batch);}
        fantasma.draw(batch);
    }

    public Camera getCamera() {
        return camera;
    }
}
