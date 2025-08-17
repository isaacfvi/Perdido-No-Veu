package com.mygdx.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.Camera;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.game.Collision;
import com.mygdx.scenes.Mansion;

public class Controller {

    private Mansion mansion;
    private Fantasma fantasma;
    private Jogador jogador;
    private Camera camera;


    public Controller(MeuInputProcessor meuInput, Assets assets) {
        this.jogador = Jogador.create(meuInput, assets, 80, 50, 50);
        this.camera = new Camera(jogador);
        this.mansion = new Mansion(jogador);

        Collision collision = Collision.getInstance();
        mansion.generateMap(assets);

        collision.setUpMap(mansion.getMap());

        this.fantasma = Fantasma.create(assets, 90, 150, 150, jogador, mansion.getMap());

        collision.inscreverEntidade(fantasma);
        collision.inscreverEntidade(jogador);
    }

    public void update(float delta){
        if(jogador.isAlive()){
            jogador.update(delta);
            camera.update(delta);
        }
        fantasma.update(delta);
        mansion.update(delta);
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
