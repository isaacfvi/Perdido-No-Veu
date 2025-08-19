package com.mygdx.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.core.Assets;
import com.mygdx.entities.Camera;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.world.Collision;
import com.mygdx.world.Mansion;

public class Controller {

    private Mansion mansion;
    private Fantasma fantasma;
    private Jogador jogador;
    private Camera camera;


    public Controller(MeuInputProcessor meuInput, Assets assets) {
        this.jogador = Jogador.create(meuInput, assets, 80, 35*32, 35*32);
        this.mansion = new Mansion(jogador);

        Collision collision = Collision.getInstance();
        collision.setUpMap(mansion.getMap(assets));

        this.fantasma = Fantasma.create(assets, 50, 150, 150, jogador, mansion.getMap(null));

        collision.inscreverEntidade(fantasma);
        collision.inscreverEntidade(jogador);

        this.camera = new Camera(jogador);
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
