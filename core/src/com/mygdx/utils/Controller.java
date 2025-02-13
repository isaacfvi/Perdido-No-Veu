package com.mygdx.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.Camera;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.scenes.Mansion;

public class Controller {

    private Mansion mansion;
    private Fantasma fantasma;
    private Jogador jogador;
    private Camera camera;


    public Controller(MeuInputProcessor meuInput, Assets assets) {
        this.camera = new Camera(meuInput, 80);
        this.fantasma = new Fantasma(assets, 35);
        this.jogador = new Jogador(meuInput, assets, 80);
        this.mansion = new Mansion(assets);

    }

    public void update(float delta){
        if(jogador.isAlive()){
            jogador.update(delta);
            camera.update(delta);
        }
        fantasma.setAlvo(jogador.getPosicao());
        fantasma.update(delta);
        mansion.update();
        //verifica_colisoes();
    }

    public void draw(SpriteBatch batch){
        mansion.drawFloor(batch);
        if(jogador.isAlive()){jogador.draw(batch);}
        fantasma.draw(batch);
        mansion.drawWalls(batch);
    }

    public void dispose(){
        fantasma.dispose();
        jogador.dispose();
        //mansion.dispose();
    }

    public Camera getCamera() {
        return camera;
    }
}
