package com.mygdx.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
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
    private ShapeRenderer shapeRenderer;


    public Controller(MeuInputProcessor meuInput, Assets assets) {
        this.jogador = Jogador.create(meuInput, assets, 80, 0, 0);
        this.mansion = new Mansion(jogador);

        Collision collision = Collision.getInstance();
        collision.setUpMap(mansion.getMap(assets));

        Vector2 fantasmaIniPosition = mansion.getEntityInitPosition();

        this.fantasma = Fantasma.create(assets, 50, fantasmaIniPosition.x, fantasmaIniPosition.y, jogador, mansion.getMap(null));

        collision.inscreverEntidade(fantasma);
        collision.inscreverEntidade(jogador);

        this.camera = new Camera(jogador);

        this.shapeRenderer = new ShapeRenderer();
    }

    public void update(float delta){
        if(jogador.isAlive()){
            jogador.update(delta);
            camera.update(delta);
        }
        fantasma.update(delta);
        mansion.update(delta);

        fantasma.setTarget(mansion.checkTrap());
    }

    public void draw(SpriteBatch batch){
        mansion.draw(batch);
        if(jogador.isAlive()){jogador.draw(batch);}
        fantasma.draw(batch);
    }

    public void drawPaths(Matrix4 projectionMatrix) {
        fantasma.drawPath(projectionMatrix);
    }

    public void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(100, 100, 50, 50);

        fantasma.drawVision(shapeRenderer);

        shapeRenderer.end();
    }

    public Camera getCamera() {
        return camera;
    }

    public void dispose() {mansion.dispose();}
}
