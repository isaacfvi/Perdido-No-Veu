package com.mygdx.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.scenes.Mansion;
import com.mygdx.utils.Assets;

public class Controller {

    private Mansion mansion;
    private Fantasma fantasma;
    private Jogador jogador;
    private Camera camera;


    public Controller(MeuInputProcessor meuInput, Assets assets) {
        this.camera = new Camera(meuInput);
        this.fantasma = new Fantasma(assets);
        this.jogador = new Jogador(meuInput, assets);
        this.mansion = new Mansion(assets);

    }

    public void update(float delta){
        if(jogador.isAlive()){
            jogador.update(delta);
            camera.update(delta);
        }
        fantasma.update(delta, jogador.getPosicao());
        mansion.update();
        verifica_colisoes();
    }

    public void draw(SpriteBatch batch){
        mansion.draw(batch);
        if(jogador.isAlive()){jogador.draw(batch);}
        fantasma.draw(batch);
    }

    public void dispose(){
        fantasma.dispose();
        jogador.dispose();
        mansion.dispose();
    }

    public void verifica_colisoes(){
        if(fantasma.gethitboxRectangle().overlaps(jogador.gethitbox())){
            jogador.died();
        }
    }

    public Camera getCamera() {
        return camera;
    }
}
